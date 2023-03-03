package cn.xiaojuzi.travel.service;

import cn.xiaojuzi.travel.domain.StrategyTheme;
import cn.xiaojuzi.travel.query.StrategyThemeQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 攻略主题服务接口
 * author:xiaojuzi
 */
public interface IStrategyThemeService extends IService<StrategyTheme>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<StrategyTheme> queryPage(StrategyThemeQuery qo);
}
