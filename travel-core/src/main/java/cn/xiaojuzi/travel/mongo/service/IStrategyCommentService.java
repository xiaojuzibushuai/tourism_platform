package cn.xiaojuzi.travel.mongo.service;

import cn.xiaojuzi.travel.mongo.domain.StrategyComment;
import cn.xiaojuzi.travel.mongo.query.StrategyCommentQuery;
import cn.xiaojuzi.travel.util.JsonResult;
import org.springframework.data.domain.Page;

/**
 * 攻略评论的服务层接口
 * author:xiaojuzi
 */
public interface IStrategyCommentService {

    /**
     * 添加
     * @param comment
     */
    void commentAdd(StrategyComment comment);

    /**
     * 分析
     * @param qo
     * @return
     *
     * 此处的分页返回对象时：spring-data-MongoDB跟之前的MP的 Page 不一样
     */
    Page<StrategyComment>  queryForList(StrategyCommentQuery qo);

    /**
     * 攻略评论点赞
     * @param cid
     * @param sid
     */
    void commentThumb(String cid, Long sid);
}
