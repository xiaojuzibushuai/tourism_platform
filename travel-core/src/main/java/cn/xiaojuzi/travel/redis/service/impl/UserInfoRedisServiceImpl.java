package cn.xiaojuzi.travel.redis.service.impl;

import cn.xiaojuzi.travel.domain.UserInfo;
import cn.xiaojuzi.travel.redis.service.IUserInfoRedisService;
import cn.xiaojuzi.travel.redis.util.RedisKeys;
import cn.xiaojuzi.travel.service.IUserInfoService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * author:xiaojuzi
 */
@Service
public class UserInfoRedisServiceImpl implements IUserInfoRedisService {
    @Autowired
    private StringRedisTemplate template;
    @Autowired
    IUserInfoService userInfoService;

    @Override
    public void setVerifyCode(String phone, String code) {
        //key: 唯一， 可读，  灵活， 时效
        String key = RedisKeys.REGIST_VERIFGY_CODE.join(phone);
        String value = code;
        template.opsForValue().set(key, value,RedisKeys.REGIST_VERIFGY_CODE.getTime(), TimeUnit.SECONDS);
    }
    @Override
    public String getVerifyCode(String phone) {
        String key = RedisKeys.REGIST_VERIFGY_CODE.join(phone);
        return template.opsForValue().get(key);
    }

    @Override
    public String setToken(UserInfo user) {
        //创建token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //key->  user_login_token:token
        String key = RedisKeys.USER_LOGIN_TOKEN.join(token);
        String value = JSON.toJSONString(user);   //json格式字符串
        //缓存用户对象， key：为token  value： user对象
        template.opsForValue().set(key, value,RedisKeys.USER_LOGIN_TOKEN.getTime(), TimeUnit.SECONDS);
        return token;
    }

    @Override
    public UserInfo getUserByToken(String token) {
        if (!StringUtils.hasLength(token)) {
            return null;
        }
        String key = RedisKeys.USER_LOGIN_TOKEN.join(token);
        if(template.hasKey(key)){
            //user对象json格式字符串
            String userStr = template.opsForValue().get(key);
            UserInfo userInfo = JSON.parseObject(userStr, UserInfo.class);
            //重置30分钟
            template.expire(key, RedisKeys.USER_LOGIN_TOKEN.getTime(), TimeUnit.SECONDS);
            return userInfo;
        }
        return null;
    }
}
