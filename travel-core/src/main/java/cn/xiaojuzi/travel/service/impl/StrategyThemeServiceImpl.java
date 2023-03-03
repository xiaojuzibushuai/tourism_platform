package cn.xiaojuzi.travel.service.impl;

import cn.xiaojuzi.travel.domain.StrategyTheme;
import cn.xiaojuzi.travel.mapper.StrategyThemeMapper;
import cn.xiaojuzi.travel.query.StrategyThemeQuery;
import cn.xiaojuzi.travel.service.IStrategyThemeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* 攻略主题服务接口实现
 * author:xiaojuzi
*/
@Service
@Transactional
public class StrategyThemeServiceImpl extends ServiceImpl<StrategyThemeMapper, StrategyTheme> implements IStrategyThemeService {

    @Override
    public IPage<StrategyTheme> queryPage(StrategyThemeQuery qo) {
        IPage<StrategyTheme> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<StrategyTheme> wrapper = Wrappers.<StrategyTheme>query();
        return super.page(page, wrapper);
    }
}
