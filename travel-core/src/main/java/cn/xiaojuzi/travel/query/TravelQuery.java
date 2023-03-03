package cn.xiaojuzi.travel.query;


import lombok.Getter;
import lombok.Setter;

/**
* 游记查询参数封装对象
 * author:xiaojuzi
*/
@Setter
@Getter
public class TravelQuery extends  QueryObject{

    private Long destId;

    private int dayType = -1;  //出行天数
    private int travelTimeType = -1;  //出行时间
    private int consumeType = -1;  //人均消费

    private String orderBy = "create_time";





}
