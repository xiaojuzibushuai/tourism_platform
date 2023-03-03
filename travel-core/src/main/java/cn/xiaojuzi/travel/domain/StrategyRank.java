package cn.xiaojuzi.travel.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * 攻略统计表
 * author:xiaojuzi
 */

@ApiModel("攻略统计实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("strategy_rank")
public class StrategyRank extends  BaseDomain{

    public static final Integer TYPE_ABROAD = 1;  //国外
    public static final Integer TYPE_CHINA = 2;   //国内
    public static final Integer TYPE_HOT = 3;     //热门


    @ApiModelProperty(value="目的地id")
    private Long destId;
    @ApiModelProperty(value="目的地名称")
    private String destName;
    @ApiModelProperty(value="攻略id")
    private Long strategyId;
    @ApiModelProperty(value="攻略标题")
    private String strategyTitle;
    @ApiModelProperty(value="排行类型")
    private Integer type;
    @ApiModelProperty(value="归档统计时间")
    private Date statisTime;
    @ApiModelProperty(value="归档统计数")
    private Long statisnum;
}