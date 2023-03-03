package cn.xiaojuzi.travel.mongo.service;

import cn.xiaojuzi.travel.mongo.domain.TravelComment;
import cn.xiaojuzi.travel.mongo.query.TravelCommentQuery;
import cn.xiaojuzi.travel.util.JsonResult;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 游记评论的服务层接口
 * author:xiaojuzi
 */
public interface ITravelCommentService {

    /**
     * 添加
     * @param comment
     */
    void commentAdd(TravelComment comment);

    /**
     * 分析
     * @param qo
     * @return
     *
     * 此处的分页返回对象时：spring-data-MongoDB跟之前的MP的 Page 不一样
     */
    Page<TravelComment>  queryPage(TravelCommentQuery qo);

    /**
     * 游记评论列表
     * @param travelId
     * @return
     */
    List<TravelComment> queryByTravelId(Long travelId);
}
