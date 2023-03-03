package cn.xiaojuzi.travel.controller;

import cn.xiaojuzi.travel.annotation.RequireLogin;
import cn.xiaojuzi.travel.annotation.UserParam;
import cn.xiaojuzi.travel.domain.Strategy;
import cn.xiaojuzi.travel.domain.StrategyContent;
import cn.xiaojuzi.travel.domain.UserInfo;
import cn.xiaojuzi.travel.mongo.domain.StrategyComment;
import cn.xiaojuzi.travel.mongo.query.StrategyCommentQuery;
import cn.xiaojuzi.travel.mongo.service.IStrategyCommentService;
import cn.xiaojuzi.travel.query.StrategyQuery;
import cn.xiaojuzi.travel.redis.service.IStrategyStatisVORedisService;
import cn.xiaojuzi.travel.redis.service.IUserInfoRedisService;
import cn.xiaojuzi.travel.service.IStrategyConditionService;
import cn.xiaojuzi.travel.service.IStrategyRankService;
import cn.xiaojuzi.travel.service.IStrategyService;
import cn.xiaojuzi.travel.service.IStrategyThemeService;
import cn.xiaojuzi.travel.util.JsonResult;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: tourism_platform
 * @description:前端攻略相关接口
 * @author: xiaojuzi
 * @create: 2022-03-08 10:45
 **/
@RestController
@Api(tags = "前端攻略相关接口")
@RequestMapping("/strategies")
public class StrategyController {

    @Autowired
    private IStrategyService strategyService;

    @Autowired
    private IStrategyThemeService strategyThemeService;

    @Autowired
    private IStrategyRankService strategyRankService;

    @Autowired
    private IStrategyConditionService strategyConditionService;

    @Autowired
    private IStrategyCommentService strategyCommentService;

    @Autowired
    private IUserInfoRedisService userInfoRedisService;

    @Autowired
    private IStrategyStatisVORedisService strategyStatisVORedisService;


    @GetMapping("/content")
    public Object content(Long id){
        return JsonResult.success(strategyService.getContent(id));
    }

    @GetMapping("/detail")
    public Object detail(Long id){
        Strategy strategy = strategyService.getById(id);
        StrategyContent con = strategyService.getContent(id);
        strategy.setContent(con);

        //阅读数 + 1
        strategyStatisVORedisService.increaseViewNum(id);

        return JsonResult.success(strategy);
    }

    @GetMapping("/themes")
    public Object themes(){

        return JsonResult.success(strategyThemeService.list());
    }



    @GetMapping("/query")
    public Object query(StrategyQuery qo){

        return JsonResult.success(strategyService.queryPage(qo));
    }


    @GetMapping("/rank")
    public Object rank(int type){

        return JsonResult.success(strategyRankService.queryRank(type));
    }

    @GetMapping("/condition")
    public Object condition(int type){
        return JsonResult.success(strategyConditionService.queryCondition(type));
    }


    @GetMapping("/themeCds")
    public Object themeCds(){
        return JsonResult.success(strategyService.queryThemeCommand());
    }


    @RequireLogin
    @PostMapping("/commentAdd")
    public Object commentAdd(StrategyComment comment, HttpServletRequest request){

        //用户
        String token = request.getHeader("token");
        UserInfo userInfo = userInfoRedisService.getUserByToken(token);


        //属性拷贝， 原理： 对象内省操作， 注意： 属性名必须一样
        //参数1：源数据对象 ctrl + c  参数2：目标数据对象  ctrl + v
        BeanUtils.copyProperties(userInfo, comment);
        comment.setUserId(userInfo.getId());
        //comment.setNickname(userInfo.getNickname());
        //comment.setCity(userInfo.getCity());

        strategyCommentService.commentAdd(comment);



        //评论数+1
        strategyStatisVORedisService.replynumIncrease(comment.getStrategyId());




        return JsonResult.success();
    }

    @RequireLogin
    @PostMapping("/commentThumb")
    public Object commentThumb(String cid, @UserParam UserInfo userInfo){

        //评论点赞
        strategyCommentService.commentThumb(cid, userInfo.getId());

        return JsonResult.success();
    }


    @GetMapping("/comments")
    public Object comments(StrategyCommentQuery qo){

        return JsonResult.success(strategyCommentService.queryForList(qo));
    }

    @GetMapping("/statisVo")
    public Object statisVo(Long sid){

        return JsonResult.success(strategyStatisVORedisService.getStrategyVO(sid));
    }


    @RequireLogin
    @PostMapping("/favor")
    public Object favor(Long sid, @UserParam UserInfo userInfo){
        boolean ret = strategyStatisVORedisService.favor(sid, userInfo.getId());
        return JsonResult.success(ret);
    }

    @RequireLogin
    @PostMapping("/strategyThumbup")
    public Object strategyThumbup(Long sid, @UserParam UserInfo userInfo){
        boolean ret = strategyStatisVORedisService.strategyThumbup(sid, userInfo.getId());
        return JsonResult.success(ret);
    }

}
