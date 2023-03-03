package cn.xiaojuzi.travel.query;


import lombok.Getter;
import lombok.Setter;

/**
* 攻略明细查询参数封装对象
 * author:xiaojuzi
*/
@Setter
@Getter
public class StrategyQuery extends  QueryObject{

    private Long destId;
    private Long themeId;

    private String orderBy="viewnum";  //排序


    private Integer type;  //1:国外， 2：国内， 3：主题
    private Long refid;  //关联id 可能是目的地id 也可能是主题id




}
