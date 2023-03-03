package cn.xiaojuzi.travel.controller;


import cn.xiaojuzi.travel.domain.Destination;
import cn.xiaojuzi.travel.domain.Strategy;
import cn.xiaojuzi.travel.domain.Travel;
import cn.xiaojuzi.travel.domain.UserInfo;
import cn.xiaojuzi.travel.search.domain.DestinationEs;
import cn.xiaojuzi.travel.search.domain.StrategyEs;
import cn.xiaojuzi.travel.search.domain.TravelEs;
import cn.xiaojuzi.travel.search.domain.UserInfoEs;
import cn.xiaojuzi.travel.search.service.IDestinationEsService;
import cn.xiaojuzi.travel.search.service.IStrategyEsService;
import cn.xiaojuzi.travel.search.service.ITravelEsService;
import cn.xiaojuzi.travel.search.service.IUserInfoEsService;
import cn.xiaojuzi.travel.service.IDestinationService;
import cn.xiaojuzi.travel.service.IStrategyService;
import cn.xiaojuzi.travel.service.ITravelService;
import cn.xiaojuzi.travel.service.IUserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
/**
 * @program: tourism_platform
 * @description:
 * @author: xiaojuzi
 * @create: 2022-03-08 10:45
 **/
@ApiIgnore
@RestController
public class DataController {

    //es服务

    @Autowired
    private IDestinationEsService destinationEsService;
    @Autowired
    private IStrategyEsService strategyEsService;

    @Autowired
    private ITravelEsService travelEsService;

    @Autowired
    private IUserInfoEsService userInfoEsService;


    //mysql服务
    @Autowired
    private IDestinationService destinationService;
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private ITravelService travelService;
    @Autowired
    private IUserInfoService userInfoService;


    @ApiIgnore
    @GetMapping("/dataInit")
    public Object dataInit(){

        //攻略
        List<Strategy> sts = strategyService.list();
        for (Strategy st : sts) {
            StrategyEs es = new StrategyEs();
            BeanUtils.copyProperties(st, es);
            strategyEsService.save(es);
        }

        //游记
       List<Travel> ts = travelService.list();
        for (Travel t : ts) {
            TravelEs es = new TravelEs();
            BeanUtils.copyProperties(t, es);
            travelEsService.save(es);
        }




        //用户
        List<UserInfo> uf = userInfoService.list();
        for (UserInfo u : uf) {
            UserInfoEs es = new UserInfoEs();
            BeanUtils.copyProperties(u, es);
            userInfoEsService.save(es);
        }


        //目的地
        List<Destination> dests = destinationService.list();
        for (Destination d : dests) {
            DestinationEs es = new DestinationEs();
            BeanUtils.copyProperties(d, es);
            destinationEsService.save(es);
        }

        return "ok";
    }
}
