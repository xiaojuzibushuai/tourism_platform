package cn.xiaojuzi.travel.service;

import cn.xiaojuzi.travel.domain.StrategyRank;
import cn.xiaojuzi.travel.query.StrategyRankQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 攻略排行统计服务接口
 * author:xiaojuzi
 */
public interface IStrategyRankService extends IService<StrategyRank>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<StrategyRank> queryPage(StrategyRankQuery qo);


    /**
     * 查询指定类型攻略排行
     * @param type
     * @return
     */
    List<StrategyRank> queryRank(int type);
}
