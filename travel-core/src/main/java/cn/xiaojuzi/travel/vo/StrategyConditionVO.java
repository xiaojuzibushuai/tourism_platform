package cn.xiaojuzi.travel.vo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


/**
 * @Description 攻略条件统计表
 * @Author xiaojuzi
 */
@AllArgsConstructor
@Data
@TableName("strategy_condition")
public class StrategyConditionVO {

  public static final Integer TYPE_ABROAD = 1;  //国外

  public static final Integer TYPE_CHINA = 2;   //国内

  public static final Integer TYPE_THEME = 3;     //热门

  private String name;

  private Integer count; //个数

  private Long refid; //关联id

  private Integer type; //条件类型

  private Date statisTime; //归档统计时间


}
