package cn.xiaojuzi.travel.job;

import cn.xiaojuzi.travel.domain.Strategy;
import cn.xiaojuzi.travel.domain.StrategyRank;
import cn.xiaojuzi.travel.service.IStrategyRankService;
import cn.xiaojuzi.travel.service.IStrategyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: tourism_platform
 * @description:攻略统计排行定时任务
 * @author: xiaojuzi
 * @create: 2022-02-08 15:56
 **/

//@Component
public class StrategyRankStatisJob {
    
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private IStrategyRankService strategyRankService;


    /**
     *
     *　　Cron表达式是一个字符串，字符串以5或6个空格隔开，分为6或7个域，每一个域代表一个含义，Cron有如下两种语法格式：
     *
     * 　　（1） Seconds Minutes Hours DayofMonth Month DayofWeek Year
     *            秒      分      时    几号       月      周几     年
     *
     * 　　（2）Seconds Minutes Hours DayofMonth Month DayofWeek       spring支持
     *           秒      分      时    几号       月      周几
     *           0       0      2     1         *       ?        *   表示在每月的1日的凌晨2点调整任务
     *           0      15     10     ?         *    MON-FRI         表示周一到周五每天上午10:15执行作业
     *
     */

    private List<Strategy> queryData(String columnSql, Integer isabroad){
        QueryWrapper<Strategy> wrapper = new QueryWrapper<>();
        wrapper.eq(isabroad != null, "isabroad", isabroad).orderByDesc(columnSql).last("limit 10");
        List<Strategy> list = strategyService.list(wrapper);
        return list;
    }

    private List<StrategyRank> parseData(List<Strategy> list, Date date, int type){
        List<StrategyRank> ranks = new ArrayList<>();

        for (Strategy strategy : list) {
            StrategyRank rank = new StrategyRank();
            rank.setDestId(strategy.getDestId());
            rank.setDestName(strategy.getDestName());
            rank.setStrategyId(strategy.getId());
            rank.setStrategyTitle(strategy.getTitle());
            rank.setStatisTime(date);

            if(type == StrategyRank.TYPE_HOT){
                rank.setType(StrategyRank.TYPE_HOT);
                rank.setStatisnum(strategy.getViewNum() + strategy.getReplyNum() + 0L);
            }else if(type == StrategyRank.TYPE_ABROAD){
                rank.setType(StrategyRank.TYPE_ABROAD);
                rank.setStatisnum(strategy.getThumbsupNum() + strategy.getFavorNum() + 0L);
            }else if(type == StrategyRank.TYPE_CHINA){
                rank.setType(StrategyRank.TYPE_CHINA);
                rank.setStatisnum(strategy.getThumbsupNum() + strategy.getFavorNum() + 0L);
            }
            ranks.add(rank);
        }
        return ranks;
    }





    //定时任务标签， cron： 任务计划表达式
    @Scheduled(cron = "0/10 * * * * ?")
    public void doWork(){
        System.out.println("---------------攻略排行统计-start---------------------");
        //1:从攻略表-strategy中查询满足条件攻略数据
        //热门
        List<Strategy> hotList = this.queryData("viewnum + replynum", null);
        //海外
        List<Strategy> abroadList = this.queryData("thumbsupnum + favornum", Strategy.ABROAD_YES);
        //国内
        List<Strategy> chinaList = this.queryData("thumbsupnum + favornum", Strategy.ABROAD_NO);

        Date date = new Date();
        List<StrategyRank> ranks = new ArrayList<>();
        //2:将满足条件数据添加到分时统计表中-strategy_rank
        ranks.addAll(parseData(hotList, date, StrategyRank.TYPE_HOT));
        ranks.addAll(parseData(abroadList, date, StrategyRank.TYPE_ABROAD));
        ranks.addAll(parseData(chinaList, date, StrategyRank.TYPE_CHINA));
        strategyRankService.saveBatch(ranks);

        System.out.println("---------------攻略排行统计-end---------------------");






















    }
}
