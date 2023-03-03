package cn.xiaojuzi.travel.controller;

import cn.xiaojuzi.travel.annotation.UserParam;
import cn.xiaojuzi.travel.domain.Strategy;
import cn.xiaojuzi.travel.domain.UserInfo;
import cn.xiaojuzi.travel.redis.util.RedisKeys;
import cn.xiaojuzi.travel.service.IStrategyService;
import cn.xiaojuzi.travel.util.JsonResult;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: tourism_platform
 * @description:
 * @author: xiaojuzi
 * @create: 2022-03-08 10:45
 **/
@RestController
@Api(tags = "前端收藏相关接口")
@RequestMapping("/collections")
public class CollectionController {
    @Autowired
    IStrategyService strategyService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @ApiOperation(value = "收藏列表")
    @GetMapping("/query")
    @ResponseBody
    public JsonResult query(@UserParam UserInfo userInfo){
        List<Strategy> strategyList = new ArrayList<>();
        // 拼接key
        String key = RedisKeys.USER_STRATEGY_FAVOR.join(userInfo.getId().toString());
        String list = stringRedisTemplate.opsForValue().get(key).toString();
        List<Long> strategyIds = JSON.parseArray(list, Long.class);
        for (Long strategyId : strategyIds) {
            strategyList.add(strategyService.getById(strategyId));
        }
        return JsonResult.success(strategyList);
    }
}
