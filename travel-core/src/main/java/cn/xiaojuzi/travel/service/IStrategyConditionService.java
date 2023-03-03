package cn.xiaojuzi.travel.service;

import cn.xiaojuzi.travel.domain.StrategyCondition;
import cn.xiaojuzi.travel.query.StrategyConditionQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 攻略条件服务接口
 * author:xiaojuzi
 */
public interface IStrategyConditionService extends IService<StrategyCondition>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<StrategyCondition> queryPage(StrategyConditionQuery qo);


    /**
     * 攻略条件统计对象
     * @param type
     * @return
     */
    List<StrategyCondition> queryCondition(int type);
}
