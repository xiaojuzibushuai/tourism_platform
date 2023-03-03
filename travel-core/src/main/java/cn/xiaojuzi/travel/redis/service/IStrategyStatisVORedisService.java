package cn.xiaojuzi.travel.redis.service;



import cn.xiaojuzi.travel.redis.vo.StrategyStatisVO;
import cn.xiaojuzi.travel.util.JsonResult;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 攻略统计数缓存操作服务
 * author:xiaojuzi
 */
public interface IStrategyStatisVORedisService {


    /**
     * 阅读数+1
     * @param sid
     */
    void increaseViewNum(Long sid) ;

    /**
     * 获取vo对象
     * @param sid
     * @return
     */
    StrategyStatisVO getStrategyVO(Long sid);

    /**
     * 缓存vo对象
     * @param vo
     * @return
     */
    void setStrategyVO(StrategyStatisVO vo);

    /**
     * 评论数+1
     * @param sid
     */
    void replynumIncrease(Long sid);


    /**
     * 攻略收藏操作
     * @param sid
     * @param uid
     * @return true：收藏成功， false： 取消收藏
     */
    boolean favor(Long sid, Long uid);

    /**
     * 用户攻略id收藏集合
     * @param userId
     * @return
     */
    List<Long> getSidList(Long userId);

    /**
     * 攻略顶操作
     * @param sid
     * @param uid
     * @return true：顶成功， false： 今天顶过
     */
    boolean strategyThumbup(Long sid, Long uid);

    /**
     * 判断指定id攻略vo对象是否存在
     * @param sid
     * @return  true: 存在， false：不存在
     */
    boolean isVoExists(Long sid);

    /**
     * 根据指定规则查询vo对象
     * @param pattern
     * @return
     */
    List<StrategyStatisVO> queryVOByPattern(String pattern);
}
