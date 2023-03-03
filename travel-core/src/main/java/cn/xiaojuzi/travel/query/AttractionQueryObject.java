package cn.xiaojuzi.travel.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: tourism_platform
 * @description:景点的查询QO
 * @author: xiaojuzi
 * @create: 2022-03-08 10:03
 **/
@ApiModel(value="景点的查询")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttractionQueryObject extends QueryObject{
    @ApiModelProperty(value="查询详情的id")
    private Long id;

}
