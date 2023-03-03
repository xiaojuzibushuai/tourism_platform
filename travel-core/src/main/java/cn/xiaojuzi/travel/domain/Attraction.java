package cn.xiaojuzi.travel.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: tourism_platform
 * @description: 景点表
 * @author: xiaojuzi
 * @create: 2022-02-07 10:15
 **/

@ApiModel(value="景点实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attraction {
    @ApiModelProperty(value="id主键")
    private Long id;

    @ApiModelProperty(value="多少人去过")
    private Long went;

    @ApiModelProperty(value="费时参考")
    private Integer timeCost;

    @ApiModelProperty(value="景点名字")
    private String name;

    @ApiModelProperty(value="景点图片")
    private String image;

    @ApiModelProperty(value="电话")
    private String phone;

    @ApiModelProperty(value="梗概")
    private String summary;

    @ApiModelProperty(value="网址")
    private String url;

    @ApiModelProperty(value="内容")
    private String  content;

}
