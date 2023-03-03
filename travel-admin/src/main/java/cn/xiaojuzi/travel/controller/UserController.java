package cn.xiaojuzi.travel.controller;

import cn.xiaojuzi.travel.domain.UserInfo;
import cn.xiaojuzi.travel.redis.service.IUserInfoRedisService;
import cn.xiaojuzi.travel.service.IUserInfoService;
import cn.xiaojuzi.travel.util.JsonResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: tourism_platform
 * @description:
 * @author: xiaojuzi
 * @create: 2022-03-09 15:08
 **/
@Api(tags = "用户相关接口")
@RestController
public class UserController {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserInfoRedisService userInfoRedisService;

    @RequestMapping("/login")
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
}
