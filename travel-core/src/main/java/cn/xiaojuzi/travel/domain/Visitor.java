package cn.xiaojuzi.travel.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @program: tourism_platform
 * @description: 游客实体类
 * @author: xiaojuzi
 * @create: 2022-02-07 10:35
 **/


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("visitor")
@Accessors(chain = true)
@ApiModel(description = "访客信息表")
public class Visitor  extends BaseDomain{
    @ApiModelProperty(value = "游客id")
    private Long visitorId;
    @ApiModelProperty(value = "所有者id")
    private Long ownerId;

    @TableField(exist = false)
    private UserInfo visitor;

    public Visitor(Long visitorId, Long ownerId) {
        this.visitorId = visitorId;
        this.ownerId = ownerId;
    }
}
