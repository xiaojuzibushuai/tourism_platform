package cn.xiaojuzi.travel.redis.service.impl;

import cn.xiaojuzi.travel.domain.Strategy;
import cn.xiaojuzi.travel.redis.service.IStrategyStatisVORedisService;
import cn.xiaojuzi.travel.redis.util.RedisKeys;
import cn.xiaojuzi.travel.redis.vo.StrategyStatisVO;
import cn.xiaojuzi.travel.service.IStrategyService;
import cn.xiaojuzi.travel.util.DateUtil;
import cn.xiaojuzi.travel.util.JsonResult;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * author:xiaojuzi
 */
@Service
public class StrategyStatisVORedisServiceImpl implements IStrategyStatisVORedisService {

    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private IStrategyService strategyService;

    @Override
    public StrategyStatisVO getStrategyVO(Long sid) {
        //拼接vo的key  : strategy_statis_vo:sid
        String key = RedisKeys.STRATEGY_STATIS_VO.join(sid.toString());
        StrategyStatisVO vo = null;
        if(!template.hasKey(key)){
            //不存在， 进行vo对象初始化
            vo = new StrategyStatisVO();
            Strategy strategy = strategyService.getById(sid);
            BeanUtils.copyProperties(strategy, vo);
            vo.setStrategyId(strategy.getId());
            template.opsForValue().set(key, JSON.toJSONString(vo));
        }else{
            String voStr = template.opsForValue().get(key);
            vo = JSON.parseObject(voStr,StrategyStatisVO.class );
        }
        return vo;
    }
    @Override
    public void setStrategyVO(StrategyStatisVO vo) {
        //拼接vo的key  : strategy_statis_vo:sid
        String key = RedisKeys.STRATEGY_STATIS_VO.join(vo.getStrategyId().toString());
        //更新vo
        template.opsForValue().set(key, JSON.toJSONString(vo));
    }

    @Override
    public void replynumIncrease(Long sid) {
        StrategyStatisVO vo = this.getStrategyVO(sid);
        //评论数+1
        vo.setReplynum(vo.getReplynum() + 1);
        this.setStrategyVO(vo);
    }
    @Override
    public void increaseViewNum(Long sid) {
        //获取vo对象
        StrategyStatisVO vo = this.getStrategyVO(sid);
        //阅读数 + 1
        vo.setViewnum(vo.getViewnum() + 1);
        this.setStrategyVO(vo);
    }


    @Override
    public boolean favor(Long sid, Long uid) {
        //拼接sidList的key
        String key = RedisKeys.USER_STRATEGY_FAVOR.join(uid.toString());
        //如果key不存在， 初始化
        List<Long> sidList = null;
        if(!template.hasKey(key)){
            sidList = new ArrayList<>();
            //从mysql中查询用户攻略收藏表【暂时没有】
        }else{
            String str = template.opsForValue().get(key);
            //参数1：json格式字符串， 参数2：集合元素泛型
            sidList = JSON.parseArray(str, Long.class);
        }
        StrategyStatisVO vo = this.getStrategyVO(sid);
        boolean flag = false;
        //判断sid是否在sidList中
        if(sidList.contains(sid)){
            //如果在收藏数-1，sid移除出sidList  【取消收藏】
            vo.setFavornum(vo.getFavornum() - 1);
            sidList.remove(sid);
        }else{
            //如果不在收藏数+1，sid添加sidList中 【收藏】
            vo.setFavornum(vo.getFavornum() + 1);
            sidList.add(sid);

            flag = true;
        }
        //更新sidList    vo对象
        template.opsForValue().set(key, JSON.toJSONString(sidList));
        this.setStrategyVO(vo);
        //return flag;
        return sidList.contains(sid);
    }

    @Override
    public List<Long> getSidList(Long userId) {
        //拼接sidList的key
        String key = RedisKeys.USER_STRATEGY_FAVOR.join(userId.toString());
        //如果key不存在， 初始化
        List<Long> sidList = null;
        if(!template.hasKey(key)){
            sidList = new ArrayList<>();
            //从mysql中查询用户攻略收藏表【暂时没有】
            template.opsForValue().set(key, JSON.toJSONString(sidList));
        }else{
            String str = template.opsForValue().get(key);
            //参数1：json格式字符串， 参数2：集合元素泛型
            sidList = JSON.parseArray(str, Long.class);
        }
        return sidList;
    }

    @Override
    public boolean strategyThumbup(Long sid, Long uid) {
        //拼接key
        String key = RedisKeys.STRATEGY_THUMB.join(uid.toString(), sid.toString());
        //判断key是否存在
        if(!template.hasKey(key)){
            //不存在， 点赞数+ 1 ， 缓存标记 ，设置有效时间【点赞操作】
            StrategyStatisVO vo = this.getStrategyVO(sid);
            vo.setThumbsupnum(vo.getThumbsupnum() + 1);
            //更新vo对象
            this.setStrategyVO(vo);
            //当前时间
            Date now = new Date();
            //最后一秒
            Date end = DateUtil.getEndDate(now);

            //当前时间到今天最后一秒
            Long time = DateUtil.getDateBetween(now, end);
            //缓存标记
            template.opsForValue().set(key, "1", time, TimeUnit.SECONDS);
            return true;
        }
        //存在， 不做任何处理 【已经顶过】
        return false;
    }

    @Override
    public boolean isVoExists(Long sid) {
        String key = RedisKeys.STRATEGY_STATIS_VO.join(sid.toString());
        return template.hasKey(key);
    }


    @Override
    public List<StrategyStatisVO> queryVOByPattern(String pattern) {
        List<StrategyStatisVO> vos = new ArrayList<>();
        // keys strategy_statis_vo:*
        //所有vo对象的key集合
        Set<String> keys = template.keys(RedisKeys.STRATEGY_STATIS_VO.join(pattern));
        if(keys != null && keys.size() > 0){
            for (String key : keys) {
                String voStr = template.opsForValue().get(key);
                vos.add(JSON.parseObject(voStr, StrategyStatisVO.class));
            }
        }
        return vos;
    }
}
