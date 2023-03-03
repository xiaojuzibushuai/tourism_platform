package cn.xiaojuzi.travel.controller;


import cn.xiaojuzi.travel.annotation.RequireLogin;
import cn.xiaojuzi.travel.domain.UserInfo;
import cn.xiaojuzi.travel.redis.service.IStrategyStatisVORedisService;
import cn.xiaojuzi.travel.redis.service.IUserInfoRedisService;
import cn.xiaojuzi.travel.service.IUserInfoService;
import cn.xiaojuzi.travel.swagger2.Person;
import cn.xiaojuzi.travel.util.JsonResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @program: tourism_platform
 * @description:用户资源控制接口
 * @author: xiaojuzi
 * @create: 2022-03-08 10:45
 **/
@Api(tags ="用户资源控制接口",value = "用户资源",description = "用户资源控制器")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserInfoRedisService userInfoRedisService;

    @Autowired
    private IStrategyStatisVORedisService strategyStatisVORedisService;

    @GetMapping("/get")
    public Object get(Long id){
        return userInfoService.getById(id);
    }

    @GetMapping("/checkPhone")
    public Object checkPhone(String phone){
        //检查手机号码是否唯一
        boolean ret = userInfoService.checkPhone(phone);
        return ret;
    }
    @GetMapping("/sendVerifyCode")
    public Object sendVerifyCode(String phone){

        //短信发送逻辑
        userInfoService.sendVerifyCode(phone);

        return JsonResult.success();
    }


    @ApiOperation(value = "注册功能",notes = "其实就是新增用户")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "手机号",name = "phone",dataType = "String",required = true),
            @ApiImplicitParam(value = "昵称",name = "nickname",dataType = "String",required = true),
            @ApiImplicitParam(value = "密码",name = "password",dataType = "String",required = true),
            @ApiImplicitParam(value = "确认密码",name = "rpassword",dataType = "String",required = true),
            @ApiImplicitParam(value = "验证码",name = "verifyCode",dataType = "String",required = true)
    })

    @PostMapping("/regist")
    public Object regist(String phone, String nickname, String password, String rpassword, String verifyCode){

        try {
            userInfoService.regist(phone, nickname, password, rpassword, verifyCode);
            return JsonResult.success();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.error(JsonResult.CODE_ERROR,e.getMessage(),null);
        }

    }

    @GetMapping("/login")
    public Object login(String username, String password){
        //登录
        UserInfo user = userInfoService.login(username, password);
        //登录成功： 创建token， 缓存user对象到redis中
        String token = userInfoRedisService.setToken(user);
        //user
        //token
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("token", token);
        return JsonResult.success(map);
    }



    //获取当前登录用户信息：前提：token 作为redis的key
    @RequireLogin
    @GetMapping("/currentUser")
    public Object currentUser(HttpServletRequest request){
        String token = request.getHeader("token");
        //以token换取用户对象
        UserInfo userInfo = userInfoRedisService.getUserByToken(token);
        return JsonResult.success(userInfo);
    }
    @GetMapping("/strategies/favor")
    public Object favor(Long sid, Long  userId){
        List<Long> sidList = strategyStatisVORedisService.getSidList(userId);
        return JsonResult.success(sidList.contains(sid));
    }

    @ApiResponses({
            @ApiResponse(code=200,message="用户注册成功")
    })
    @GetMapping("/test")
    public Object test(Person person){
        return JsonResult.success(person);
    }
}
