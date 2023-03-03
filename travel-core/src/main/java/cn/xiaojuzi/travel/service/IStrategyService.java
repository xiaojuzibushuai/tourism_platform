package cn.xiaojuzi.travel.service;

import cn.xiaojuzi.travel.domain.Strategy;
import cn.xiaojuzi.travel.domain.StrategyContent;
import cn.xiaojuzi.travel.query.StrategyQuery;
import cn.xiaojuzi.travel.redis.vo.StrategyStatisVO;
import cn.xiaojuzi.travel.util.JsonResult;
import cn.xiaojuzi.travel.vo.ThemeVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 攻略明细服务接口
 * author:xiaojuzi
 */
public interface IStrategyService extends IService<Strategy>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<Strategy> queryPage(StrategyQuery qo);

    /**
     * 内容查询
     * @param id
     * @return
     */
    StrategyContent getContent(Long id);

    /**
     * 点击量前3攻略集合
     * @param destId
     * @return
     */
    List<Strategy> queryViewnumTop3(Long destId);

    /**
     * vo 对象持久化
     *
     * @param vo
     */
    void updateStatisVO(StrategyStatisVO vo);

    /**
     * 查询指定目的地下攻略
     * @param destId
     * @return
     */
    List<Strategy> queryByDestId(Long destId);

    /**
     * 主题推荐
     * @return
     */
    List<ThemeVO> queryThemeCommand();

    /**
     *修改攻略状态
     * @param state
     * @param id
     * @return
     */
    JsonResult updateState(Integer state, Long id);
}
