package cn.xiaojuzi.travel.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @program: tourism_platform
 * @description: 游客数量
 * @author: xiaojuzi
 * @create: 2022-02-07 10:37
 **/
@Data
@TableName("visitor_num")
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "访客数量表")
public class VisitorNum extends BaseDomain{

    @ApiModelProperty(value = "所有者id")
    private Long ownerId;
    @ApiModelProperty(value = "今日游客访问量")
    private Long todayVisitorNumber;
    @ApiModelProperty(value = "总共游客访问量")
    private Long totalVisitorNumber;

    public VisitorNum(Long ownerId, Long todayVisitorNumber, Long totalVisitorNumber) {
        this.ownerId = ownerId;
        this.todayVisitorNumber = todayVisitorNumber;
        this.totalVisitorNumber = totalVisitorNumber;
    }
}
