package cn.xiaojuzi.travel.query;

import lombok.Getter;
import lombok.Setter;

/**
 * 目的地查询参数封装对象
 * author:xiaojuzi
 */
@Setter
@Getter
public class DestinationQuery extends  QueryObject {
    private Long parentId; //父目的地id
}
