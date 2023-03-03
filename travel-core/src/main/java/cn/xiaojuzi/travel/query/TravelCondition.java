package cn.xiaojuzi.travel.query;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * author:xiaojuzi
 */
@Getter
public class TravelCondition {

    //旅游天数
    public static final Map<Integer, TravelCondition> DAY_MAP = new HashMap<>();
    //人均消费 avg_consume
    public static final Map<Integer, TravelCondition> AVG_MAP = new HashMap<>();
    //出发时间travel_time
    public static final Map<Integer, TravelCondition> TIME_MAP = new HashMap<>();

    static{
        DAY_MAP.put(1, new TravelCondition(0, 3));
        DAY_MAP.put(2, new TravelCondition(4, 7));
        DAY_MAP.put(3, new TravelCondition(8, 14));
        DAY_MAP.put(4, new TravelCondition(15, Integer.MAX_VALUE));


        AVG_MAP.put(1, new TravelCondition(1, 999));
        AVG_MAP.put(2, new TravelCondition(1000, 6000));
        AVG_MAP.put(3, new TravelCondition(6001, 20000));
        AVG_MAP.put(4, new TravelCondition(20001, Integer.MAX_VALUE));

        TIME_MAP.put(1, new TravelCondition(1, 2));
        TIME_MAP.put(2, new TravelCondition(3, 4));
        TIME_MAP.put(3, new TravelCondition(5, 6));
        TIME_MAP.put(4, new TravelCondition(7, 8));
        TIME_MAP.put(5, new TravelCondition(9, 10));
        TIME_MAP.put(6, new TravelCondition(11, 12));
    }

    private int min;
    private int max;
    public TravelCondition(int min, int max){
        this.min = min;
        this.max = max;
    }
}
