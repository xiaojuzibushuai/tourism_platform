package cn.xiaojuzi.travel.job;


import cn.xiaojuzi.travel.redis.service.IStrategyStatisVORedisService;
import cn.xiaojuzi.travel.redis.vo.StrategyStatisVO;
import cn.xiaojuzi.travel.service.IStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @program: tourism_platform
 * @description:将redis数据持久化到mysql中 定时任务
 * @author: xiaojuzi
 * @create: 2022-02-08 14:36
 **/

//@Component
public class RedisDataPersistenceJob {

    @Autowired
    private IStrategyStatisVORedisService strategyStatisVORedisService;

    @Autowired
    private IStrategyService strategyService;


    //10s钟操作一次
    @Scheduled(cron = "0/10 * * * * ?")
    public void doWork(){

        System.out.println("-----------------vo对象持久化-begin----------------------");

        //1:从redis中获取所有vo对象

        // keys strategy_statis_vo:*
        List<StrategyStatisVO> vos =
                strategyStatisVORedisService.queryVOByPattern("*");

        //2:遍历vo对象， 将数据更新到对应攻略表中
        for (StrategyStatisVO vo : vos) {
            strategyService.updateStatisVO(vo);
        }

        System.out.println("-----------------vo对象持久化-end----------------------");


    }
}
