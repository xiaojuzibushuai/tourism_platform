package cn.xiaojuzi.travel.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 目的地(行政地区：国家/省份/城市)
 * author:xiaojuzi
 */
@ApiModel(value="目的地实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Destination extends BaseDomain {

    @ApiModelProperty(value="名称")
    private String name;

    @ApiModelProperty(value="英文")
    private String english;

    @ApiModelProperty(value="封面url")
    private String coverUrl;

    @ApiModelProperty(value="简介")
    private String info;

    @ApiModelProperty(value="父级的name")
    private String parentName;

    @ApiModelProperty(value="父级id")
    private Long parentId;

    @ApiModelProperty(value="所有子目录")
    @TableField(exist = false)
    private List<Destination> children = new ArrayList<>();

    public String getJsonString(){
        Map<String, Object> map = new HashMap<>();
        map.put("id", super.getId());
        map.put("info", this.info);
        return JSON.toJSONString(map);
    }
}