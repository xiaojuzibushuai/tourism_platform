package cn.xiaojuzi.travel.job;

import cn.xiaojuzi.travel.domain.Strategy;
import cn.xiaojuzi.travel.domain.StrategyCondition;
import cn.xiaojuzi.travel.service.IStrategyConditionService;
import cn.xiaojuzi.travel.service.IStrategyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: tourism_platform
 * @description:攻略统计排行定时任务
 * @author: xiaojuzi
 * @create: 2022-02-08 14:42
 **/

//@Component
public class StrategyConditionStatisJob {
    
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private IStrategyConditionService strategyConditionService;
    //定时任务标签， cron： 任务计划表达式
    @Scheduled(cron = "0/10 * * * * ?")
    public void doWork(){
        System.out.println("---------------攻略条件统计-start---------------------");
        //1:查询攻略表里面符合条件
       /* select  dest_id as refid, dest_name as name,  count(id) count
        from strategy where isabroad = 0 group by dest_id, dest_name
        order by count desc*/
       //国内
       QueryWrapper<Strategy> wrapper = new QueryWrapper<>();
       wrapper.select("dest_id as refid, dest_name as name, count(id) count")
               .eq("isabroad", Strategy.ABROAD_NO)
               .groupBy("dest_id", "dest_name")
               .orderByDesc("count");
       List<Map<String, Object>> chinaList = strategyService.listMaps(wrapper);

        //国外
        wrapper.clear();
        wrapper.select("dest_id as refid, dest_name as name, count(id) count")
                .eq("isabroad", Strategy.ABROAD_YES)
                .groupBy("dest_id", "dest_name")
                .orderByDesc("count");
        List<Map<String, Object>> abroadList = strategyService.listMaps(wrapper);


        //主题
        wrapper.clear();
        wrapper.select("theme_id as refid, theme_name as name, count(id) count")
                .groupBy("theme_id", "theme_name")
                .orderByDesc("count");
        List<Map<String, Object>> themeList = strategyService.listMaps(wrapper);

       Date date = new Date(); //统计时间
       List<StrategyCondition> scs = new ArrayList<>();
        //2:将符合条件数据添加到攻略条件表中
       for (Map<String, Object> map : chinaList) {
           StrategyCondition sc = new StrategyCondition();
           sc.setRefid(Long.parseLong(map.get("refid").toString()));
           sc.setName(map.get("name").toString());
           sc.setCount(Integer.parseInt(map.get("count").toString()));
           sc.setType(StrategyCondition.TYPE_CHINA);
           sc.setStatisTime(date);
           scs.add(sc);
       }
        for (Map<String, Object> map : abroadList) {
            StrategyCondition sc = new StrategyCondition();
            sc.setRefid(Long.parseLong(map.get("refid").toString()));
            sc.setName(map.get("name").toString());
            sc.setCount(Integer.parseInt(map.get("count").toString()));
            sc.setType(StrategyCondition.TYPE_ABROAD);
            sc.setStatisTime(date);
            scs.add(sc);
        }

        for (Map<String, Object> map : themeList) {
            StrategyCondition sc = new StrategyCondition();
            sc.setRefid(Long.parseLong(map.get("refid").toString()));
            sc.setName(map.get("name").toString());
            sc.setCount(Integer.parseInt(map.get("count").toString()));
            sc.setType(StrategyCondition.TYPE_THEME);
            sc.setStatisTime(date);
            scs.add(sc);
        }
        strategyConditionService.saveBatch(scs);
        System.out.println("---------------攻略条件统计-end---------------------");

    }
}
