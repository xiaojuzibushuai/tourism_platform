package cn.xiaojuzi.travel.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


import java.util.HashMap;
import java.util.Map;

/**
 * 游记推荐
 * author:xiaojuzi
 */


@ApiModel(value="游记推荐")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("banner")
public class Banner  extends BaseDomain {

    public static final int STATE_NORMAL = 0;   //正常
    public static final int STATE_DISABLE = 1;  //禁用

    public static final int TYPE_TRAVEL = 1;  //游记
    public static final int TYPE_STRATEGY = 2;  //攻略

    @ApiModelProperty(value="关联id")
    private Long refid;

    @ApiModelProperty(value="标题")
    private String title;

    @ApiModelProperty(value="副标题")
    private String subtitle;

    @ApiModelProperty(value="封面")
    private String coverUrl;

    @ApiModelProperty(value="状态")
    private Integer state = STATE_NORMAL;

    @ApiModelProperty(value="排序")
    private Integer seq;

    @ApiModelProperty(value="类型")
    private Integer type;

    public String getJsonString(){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("title",title);
        map.put("subtitle",subtitle);
        map.put("coverUrl",coverUrl);
        map.put("state",state);
        map.put("seq",seq);
        map.put("refid",refid);
        map.put("type",type);
        return JSON.toJSONString(map);
    }

    public String getStateDisplay(){
        return state == STATE_NORMAL?"正常":"禁用";
    }
    public String getTypeDisplay(){
        return type == TYPE_STRATEGY?"攻略":"游记";
    }

}