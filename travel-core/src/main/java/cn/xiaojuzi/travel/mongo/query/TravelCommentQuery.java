package cn.xiaojuzi.travel.mongo.query;

import cn.xiaojuzi.travel.query.QueryObject;
import lombok.Getter;
import lombok.Setter;

/**
 * author:xiaojuzi
 */
@Setter
@Getter
public class TravelCommentQuery extends QueryObject {
    private Long travelId;
}
