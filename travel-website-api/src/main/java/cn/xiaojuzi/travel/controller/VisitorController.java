package cn.xiaojuzi.travel.controller;

import cn.xiaojuzi.travel.annotation.UserParam;
import cn.xiaojuzi.travel.domain.UserInfo;
import cn.xiaojuzi.travel.domain.Visitor;
import cn.xiaojuzi.travel.domain.VisitorNum;
import cn.xiaojuzi.travel.service.IUserInfoService;
import cn.xiaojuzi.travel.service.VisitorNumService;
import cn.xiaojuzi.travel.service.VisitorService;
import cn.xiaojuzi.travel.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @program: tourism_platform
 * @description:访客相关接口
 * @author: xiaojuzi
 * @create: 2022-03-08 10:50
 **/
@RestController
@Api(tags = "访客相关接口")
@RequestMapping("/visitor")
public class VisitorController {
    @Autowired
    VisitorService visitorService;

    @Autowired
    IUserInfoService userInfoService;

    @Autowired
    VisitorNumService visitorNumService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "查询用户的所有访客信息")
    @GetMapping("/query")
    @ApiImplicitParam(name = "ownerId", value = "用户id")
    public JsonResult query(Long ownerId, @UserParam UserInfo userInfo){
        // 先增加访客数量
        visitorNumService.addVistorNum(ownerId,userInfo.getId());
        // 查询访客信息返回给前台
        List<Visitor> visitors = visitorService.queryVisitor(ownerId,userInfo.getId());
        // 查询访客数量信息给前台
        VisitorNum visitorNum = visitorNumService.queryVisitorNum(ownerId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("visitors",visitors);
        map.put("visitorNum",visitorNum);
        return JsonResult.success(map);
    }

}
