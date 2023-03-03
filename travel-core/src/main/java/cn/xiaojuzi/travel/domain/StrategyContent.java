package cn.xiaojuzi.travel.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * 攻略内容
 * author:xiaojuzi
 */
@ApiModel(value="攻略内容实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("strategy_content")
public class StrategyContent implements Serializable {
    @ApiModelProperty(value="id")
    private Long id;

    @ApiModelProperty(value="内容")
    private String content;
}

