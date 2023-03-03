package cn.xiaojuzi.travel.service.impl;

import cn.xiaojuzi.travel.domain.StrategyCondition;
import cn.xiaojuzi.travel.mapper.StrategyConditionMapper;
import cn.xiaojuzi.travel.query.StrategyConditionQuery;
import cn.xiaojuzi.travel.service.IStrategyConditionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* 攻略条件服务接口实现
 * author:xiaojuzi
*/
@Service
@Transactional
public class StrategyConditionServiceImpl extends ServiceImpl<StrategyConditionMapper, StrategyCondition> implements IStrategyConditionService {

    @Override
    public IPage<StrategyCondition> queryPage(StrategyConditionQuery qo) {
        IPage<StrategyCondition> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<StrategyCondition> wrapper = Wrappers.<StrategyCondition>query();
        return super.page(page, wrapper);
    }


    @Override
    public List<StrategyCondition> queryCondition(int type) {
/*
        select * from strategy_condition
        where type = 3
        and statis_time in (select max(statis_time) from strategy_condition where type = 3)
        order by count desc
*/
        QueryWrapper<StrategyCondition> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type)
                .inSql("statis_time", "select max(statis_time) from strategy_condition where type = " + type)
                .orderByDesc("count");
        return super.list(wrapper);
    }
}
