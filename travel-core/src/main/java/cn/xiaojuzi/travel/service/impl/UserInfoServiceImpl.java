package cn.xiaojuzi.travel.service.impl;


import cn.xiaojuzi.travel.domain.UserInfo;
import cn.xiaojuzi.travel.exception.LogicException;
import cn.xiaojuzi.travel.mapper.UserInfoMapper;
import cn.xiaojuzi.travel.redis.service.IUserInfoRedisService;
import cn.xiaojuzi.travel.redis.util.RedisKeys;
import cn.xiaojuzi.travel.service.IUserInfoService;
import cn.xiaojuzi.travel.util.AssertUtil;
import cn.xiaojuzi.travel.util.Consts;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {


    @Autowired
    private IUserInfoRedisService userInfoRedisService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public boolean checkPhone(String phone) {

        //select * from userinfo where phone = ?
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        UserInfo userInfo = super.getOne(wrapper);
        return userInfo != null;
    }

    //@Value("${sms.url}")
    //private String url;

    @Override
    public void sendVerifyCode(String phone) {

        //创建验证码
        String code = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 4);

        //拼接短信， 并发送短信
        StringBuilder sb = new StringBuilder(100);
        sb.append("您的短信验证码是：")
                .append(code)
                .append("请在")
                .append(Consts.VERIFY_CODE_VAI_TIME)
                .append("分钟之内使用");

        System.out.println(sb.toString()); //假装发送
//        //发起http请求
//        //http请求发送封装工具类
//        RestTemplate restTemplate = new RestTemplate();
//
//        String appkey = "dd1f7d99cd632060789a56cfaa3b77ce";
//        String url = "https://way.jd.com/BABO/sms?mobile={1}&msg=【小橘子旅游平台】您的验证码是"+code+",若非本人操作请忽略&appkey={2}";
//        //参数1：http请求url
//        //参数2：指定将响应信息封装类型
//        //参数3：请求参数
//        String ret = restTemplate.getForObject(url, String.class, phone, /*sb.toString(),*/ appkey);
//        System.out.println(ret);
//
//        if(!ret.toUpperCase().contains("SUCCESS")){
//            throw new LogicException("短信发送失败");
//        }
        //缓存到redis： key phone value 验证码
        userInfoRedisService.setVerifyCode(phone, code);

    }

    @Override
    public void regist(String phone, String nickname, String password, String rpassword, String verifyCode) {

        //校验参数是否为空
        AssertUtil.hasLength(phone,"手机号码不能为空" );
        AssertUtil.hasLength(nickname,"昵称不能为空" );
        AssertUtil.hasLength(password,"密码不能为空" );
        AssertUtil.hasLength(rpassword,"确认密码不能为空" );
        AssertUtil.hasLength(verifyCode,"验证码不能为空" );

        //校验2次输入密码是否一致
        AssertUtil.isEquals(password, rpassword,"2次密码输入不一致" );

        //检验手机格式是否正确 @TODO

        //校验验证码是否正确
        String code = userInfoRedisService.getVerifyCode(phone);
        if(!verifyCode.equalsIgnoreCase(code)){
            throw  new LogicException("验证码时效或者错误");
        }

        //校验手机号码是否唯一
        if(this.checkPhone(phone)){
            throw  new  LogicException("手机号码已经被注册了");
        }
        //注册
        UserInfo userInfo = new UserInfo();
        userInfo.setNickname(nickname);
        userInfo.setPhone(phone);
        userInfo.setPassword(password);  //假装加密
        userInfo.setState(UserInfo.STATE_NORMAL);
        userInfo.setHeadImgUrl("/images/default.jpg");

        super.save(userInfo);

    }

    @Override
    public UserInfo login(String username, String password) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", username).eq("password", password);
        UserInfo userInfo = super.getOne(wrapper);
        if(userInfo == null){
            throw new LogicException("账号或密码错误");
        }
        return userInfo;
    }


    @Override
    public List<UserInfo> queryByCity(String city) {
        return super.list(Wrappers.<UserInfo>query().eq("city",city));
    }

    @Override
    public UserInfo checkUserById(long id) {
        return userInfoMapper.selectById(id);
    }

    @Override
    public boolean favor(Long sid, Long userId) {
        String key = RedisKeys.USER_STRATEGY_FAVOR.join(userId.toString());
        boolean flag = false;
        List<Long> favorNumList = null;
        if (redisTemplate.hasKey(key)){
            String favorList =  redisTemplate.opsForValue().get(key);
            favorNumList = JSONArray.parseArray(favorList,Long.class);
            if (favorNumList.contains(sid)) {
                // 说明有收藏，变为黑色星星
                flag = true;
            }
        }
        return flag;
    }
}
