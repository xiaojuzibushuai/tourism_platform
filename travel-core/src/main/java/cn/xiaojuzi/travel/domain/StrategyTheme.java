package cn.xiaojuzi.travel.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 攻略主题
 * author:xiaojuzi
 */
@ApiModel(value="攻略主题实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("strategy_theme")
public class StrategyTheme extends BaseDomain {
    public static final int STATE_NORMAL = 0; //正常
    public static final int STATE_DISABLE = 1; //禁用


    @ApiModelProperty(value="主题名称")
    private String name;

    @ApiModelProperty(value="主题状态")
    private int state = STATE_NORMAL;

    @ApiModelProperty(value="序号")
    private int seq;
    public String getStateDisplay(){
        return state == STATE_NORMAL ? "正常" : "禁用";
    }
    public String getJsonString(){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        map.put("state",state);
        map.put("seq",seq);
        return JSON.toJSONString(map);
    }
}