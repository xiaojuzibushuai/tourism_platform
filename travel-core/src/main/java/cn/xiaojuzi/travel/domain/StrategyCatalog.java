package cn.xiaojuzi.travel.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 攻略分类： 从属于某个目的地
 * author:xiaojuzi
 */
@ApiModel(value="攻略分类实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("strategy_catalog")
public class StrategyCatalog extends BaseDomain {

    public static final int STATE_NORMAL = 0;  //显示
    public static final int STATE_DISABLE = 1;  //禁用

    @ApiModelProperty(value="目的地")
    private Long destId;

    @ApiModelProperty(value="目的名称")
    private String destName;

    @ApiModelProperty(value="状态")
    private Integer state = STATE_NORMAL;

    @ApiModelProperty(value="分类名")
    private String name;

    @ApiModelProperty(value="排序")
    private Integer seq;

    @TableField(exist = false)
    private List<Strategy> strategies = new ArrayList<>();
    public String getStateDisplay(){
        return state == STATE_NORMAL ? "正常" : "禁用";
    }

    public String getJsonString(){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        map.put("state",state);
        map.put("destId",destId);
        map.put("seq",seq);
        return JSON.toJSONString(map);
    }
}