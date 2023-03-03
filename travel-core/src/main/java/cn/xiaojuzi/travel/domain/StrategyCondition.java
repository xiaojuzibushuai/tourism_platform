package cn.xiaojuzi.travel.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * 攻略条件统计表
 * author:xiaojuzi
 */
@ApiModel("攻略条件统计实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("strategy_condition")
public class StrategyCondition extends  BaseDomain{

    public static final Integer TYPE_ABROAD = 1;  //国外
    public static final Integer TYPE_CHINA = 2;   //国内
    public static final Integer TYPE_THEME = 3;     //主题

    @ApiModelProperty(value="名字")
    private String name;
    @ApiModelProperty(value="个数")
    private Integer count;
    @ApiModelProperty(value="关联id")
    private Long refid;
    @ApiModelProperty(value="条件类型")
    private Integer type;
    @ApiModelProperty(value="归档统计时间")
    private Date statisTime;
}
