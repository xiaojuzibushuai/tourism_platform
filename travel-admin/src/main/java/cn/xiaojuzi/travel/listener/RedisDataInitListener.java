package cn.xiaojuzi.travel.listener;

import cn.xiaojuzi.travel.domain.Strategy;
import cn.xiaojuzi.travel.redis.service.IStrategyStatisVORedisService;
import cn.xiaojuzi.travel.redis.vo.StrategyStatisVO;
import cn.xiaojuzi.travel.service.IStrategyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @program: tourism_platform
 * @description:redis 数据初始化监听器
 *  * ContextRefreshedEvent： spring容器更新事件
 * @author: xiaojuzi
 * @create: 2022-02-08 15:26
 **/

@Slf4j
@Component
public class RedisDataInitListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private IStrategyStatisVORedisService strategyStatisVORedisService;

    //当spring容器启动并初始化完成后执行
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("--------------------攻略统计vo对象初始化-begin------------------------------");
        //1:查询mysql中所有攻略数据List<Strategy>
        List<Strategy> list = strategyService.list();
        //2：封装成vo对象
        for (Strategy st : list) {
            /**
             *
             * 假设id:2这个攻略数据vienwum = 200， 初始化到redis中， vo里面viewnum= 200
             * 用户访问id=2攻略数据， 假设访问200次， vo里面viewnum = 400
             * 此时， mgrstie项目需要停机更新， 再次启动时， 按照现有代码逻辑会出现vo覆盖情况。
             * 原因：mysql数据库数据没改变， 再次初始化时会对redis数据做覆盖
             *
             * 解决方案：如果vo存在，跳过即可
             */
            if(strategyStatisVORedisService.isVoExists(st.getId())){
                continue;
            }
            StrategyStatisVO vo = new StrategyStatisVO();
            BeanUtils.copyProperties(st, vo);
            vo.setStrategyId(st.getId());
            //3:添加到redis缓存中
            strategyStatisVORedisService.setStrategyVO(vo);
        }

        System.out.println("--------------------攻略统计vo对象初始化-end------------------------------");
    }
}
