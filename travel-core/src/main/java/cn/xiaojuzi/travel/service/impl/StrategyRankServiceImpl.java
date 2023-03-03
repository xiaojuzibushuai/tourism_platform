package cn.xiaojuzi.travel.service.impl;

import cn.xiaojuzi.travel.domain.StrategyRank;
import cn.xiaojuzi.travel.mapper.StrategyRankMapper;
import cn.xiaojuzi.travel.query.StrategyRankQuery;
import cn.xiaojuzi.travel.service.IStrategyRankService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* 攻略排行统计服务接口实现
 * author:xiaojuzi
*/
@Service
@Transactional
public class StrategyRankServiceImpl extends ServiceImpl<StrategyRankMapper, StrategyRank> implements IStrategyRankService {

    @Override
    public IPage<StrategyRank> queryPage(StrategyRankQuery qo) {
        IPage<StrategyRank> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<StrategyRank> wrapper = Wrappers.<StrategyRank>query();
        return super.page(page, wrapper);
    }

    @Override
    public List<StrategyRank> queryRank(int type) {

       /* select * from strategy_rank
        where type = 3   and statis_time in (select max(statis_time) from strategy_rank where type = 3 )
        order by statisnum desc
        limit 10*/

       QueryWrapper<StrategyRank> wrapper = new QueryWrapper<>();
       wrapper.eq("type", type)
               .orderByDesc("statisnum")
               .inSql("statis_time", "select max(statis_time) from strategy_rank where type = " + type)
               .last("limit 10");
        return super.list(wrapper);
    }
}
