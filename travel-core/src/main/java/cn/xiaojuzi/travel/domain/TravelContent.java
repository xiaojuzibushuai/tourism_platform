package cn.xiaojuzi.travel.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * 游记内容
 * author:xiaojuzi
 */
@ApiModel(value="游记内容实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("travel_content")
public class TravelContent implements Serializable {
    @ApiModelProperty(value="id")
    private Long id;

    @ApiModelProperty(value="内容")
    private String content;
}

