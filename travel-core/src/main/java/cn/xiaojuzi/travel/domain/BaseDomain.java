package cn.xiaojuzi.travel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 基础实体类，抽取id
 * author:xiaojuzi
 */
@Data
@ApiModel(value="基础实体类")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BaseDomain {
    @ApiModelProperty(value = "主键id",example="1")
    @TableId(type = IdType.AUTO)
    protected Long id;
}
