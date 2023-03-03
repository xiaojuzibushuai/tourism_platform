package cn.xiaojuzi.travel.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 游记分条件范围查询
 * @Author xiaojuzi
 */
@Data
@NoArgsConstructor
public class TravelConditionVO {

  // 处理人均花费map
  public static Map<Integer,TravelConditionVO> TIME_TYPE = new HashMap<>();
  // 处理人均花费map
  public static Map<Integer,TravelConditionVO> MONEY_TYPE = new HashMap<>();
  // 处理天数的map
  public static Map<Integer,TravelConditionVO> DAY_TYPE = new HashMap<>();

  static{
    TIME_TYPE.put(1,new TravelConditionVO(1,2));
    TIME_TYPE.put(2,new TravelConditionVO(3,4));
    TIME_TYPE.put(3,new TravelConditionVO(5,6));
    TIME_TYPE.put(4,new TravelConditionVO(7,8));
    TIME_TYPE.put(5,new TravelConditionVO(9,10));
    TIME_TYPE.put(6,new TravelConditionVO(10,11));

    MONEY_TYPE.put(1,new TravelConditionVO(1,999));
    MONEY_TYPE.put(2,new TravelConditionVO(1000,6000));
    MONEY_TYPE.put(3,new TravelConditionVO(6000,20000));
    MONEY_TYPE.put(4,new TravelConditionVO(20000));

    DAY_TYPE.put(1,new TravelConditionVO(0,3));
    DAY_TYPE.put(2,new TravelConditionVO(4,7));
    DAY_TYPE.put(3,new TravelConditionVO(8,14));
    DAY_TYPE.put(4,new TravelConditionVO(15));
  }

  private Integer min;
  private Integer max;

  public TravelConditionVO(Integer min) {
    this.min = min;
  }

  public TravelConditionVO(Integer min, Integer max) {
    this.min = min;
    this.max = max;
  }
}
