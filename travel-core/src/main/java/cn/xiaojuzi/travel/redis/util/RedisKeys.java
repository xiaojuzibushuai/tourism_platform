package cn.xiaojuzi.travel.redis.util;

import cn.xiaojuzi.travel.util.Consts;
import cn.xiaojuzi.travel.util.DateUtil;
import lombok.Getter;

import java.util.Date;

/**
 * redis的key的管理类
 * 约定：一个redis key 映射一个枚举实例
 * author:xiaojuzi
 */
@Getter
public enum RedisKeys {
    //攻略顶操作key 实例
    BRUSH_PROOF("brush_proof", 60L),   //-1表示不设置时间
    STRATEGY_THUMB("strategy_thumb", -1L),   //-1表示不设置时间

    // 访客数量相关
    VISITOR_NUM_TODAY("visitor_num_today",getBetweenEndDate()),

    //用户攻略收藏结合key 实例
    USER_STRATEGY_FAVOR("user_strategy_favor", -1L),   //-1表示不设置时间



    //攻略统计vo对象key
    STRATEGY_STATIS_VO("strategy_statis_vo", -1L),   //-1表示不设置时间

    //用户登录key
    USER_LOGIN_TOKEN("user_login_token", Consts.USER_INFO_TOKEN_VAI_TIME * 60L ),
    //注册短信验证码key
    REGIST_VERIFGY_CODE("regist_verifgy_code", Consts.VERIFY_CODE_VAI_TIME * 60L );

    private String prefix;  // key的前缀
    private Long time;    //key有效时间， 单位是s
    private RedisKeys(String prefix, Long time){
        this.prefix = prefix;
        this.time = time;
    }
    //拼接出完整redis的key
    public String join(String... values){
        StringBuilder sb = new StringBuilder(80);
        sb.append(this.prefix);
        for (String value : values) {
            sb.append(":").append(value);
        }
        return sb.toString();
    }


    public static Long getBetweenEndDate(){
        Date endDate = DateUtil.getEndDate(new Date());
        return DateUtil.getDateBetween(new Date(), endDate);
    }
}
