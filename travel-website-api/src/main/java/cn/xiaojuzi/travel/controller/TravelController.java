package cn.xiaojuzi.travel.controller;

import cn.xiaojuzi.travel.annotation.RequireLogin;
import cn.xiaojuzi.travel.annotation.UserParam;
import cn.xiaojuzi.travel.domain.*;
import cn.xiaojuzi.travel.mongo.domain.TravelComment;
import cn.xiaojuzi.travel.mongo.service.ITravelCommentService;
import cn.xiaojuzi.travel.query.TravelQuery;
import cn.xiaojuzi.travel.redis.service.IUserInfoRedisService;
import cn.xiaojuzi.travel.service.*;
import cn.xiaojuzi.travel.util.JsonResult;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: tourism_platform
 * @description:前端游记相关接口
 * @author: xiaojuzi
 * @create: 2022-03-08 10:45
 **/
@RestController
@Api(tags = "前端游记相关接口")
@RequestMapping("/travels")
public class TravelController {

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private ITravelService travelService;

    @Autowired
    private IUserInfoRedisService userInfoRedisService;

    @Autowired
    private ITravelCommentService travelCommentService;

    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private IStrategyThemeService strategyThemeService;
    @Autowired
    private IStrategyCatalogService strategyCatalogService;


    @GetMapping("/query")
    public Object query(TravelQuery qo){
        return JsonResult.success(travelService.queryPage(qo));
    }

    @GetMapping("/detail")
    public Object detail(Long id){
        Travel travel = travelService.getById(id);
        travel.setAuthor(userInfoService.getById(travel.getAuthorId()));
        TravelContent content = travelService.getContent(id);
        travel.setContent(content);
        return JsonResult.success(travel);
    }

    @RequireLogin
    @PostMapping("/commentAdd")
    public Object commentAdd(TravelComment comment, HttpServletRequest request){

        //用户
        String token = request.getHeader("token");
        UserInfo userInfo = userInfoRedisService.getUserByToken(token);


        BeanUtils.copyProperties(userInfo, comment);
        comment.setUserId(userInfo.getId());
        travelCommentService.commentAdd(comment);
        return JsonResult.success();
    }


    @GetMapping("/comments")
    public Object comments(Long travelId){

        return JsonResult.success(travelCommentService.queryByTravelId(travelId));
    }



    @GetMapping("/info")  //希望使用自定义用户参数解析器
    public Object info(@UserParam UserInfo userInfo){
        //当前登录用户对象
        return JsonResult.success(userInfo);
    }

    @GetMapping("/updateUser")//希望使用springmvc默认参数解析器
    public Object updateUser(UserInfo userInfo){
        //当前登录用户对象
        return JsonResult.success(userInfo);
    }

    @GetMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Travel travel){
        travelService.saveOrUpdate(travel);
        return JsonResult.success();
    }

    @RequestMapping("/input")
    public String input(Model model, Long id){
        if (id != null){
            //strategy  编辑时才有
            Strategy strategy = strategyService.getById(id);
            //内容
            StrategyContent content = strategyService.getContent(id);
            strategy.setContent(content);
            model.addAttribute("strategy", strategy);
        }
        //catalogs
        model.addAttribute("catalogs", strategyCatalogService.queryGroupCatalog());
        //themes
        model.addAttribute("themes", strategyThemeService.list());

        return "strategy/input";
    }

}
