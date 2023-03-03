package cn.xiaojuzi.travel.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @program: tourism_platform
 * @description: 攻略表
 * @author: xiaojuzi
 * @create: 2022-02-07 10:17
 **/

@ApiModel(value="攻略实体类")
@Data
@TableName("collection")
public class Collection extends BaseDomain{
    @ApiModelProperty(value="攻略id")
    private Long strategyId;

    @ApiModelProperty(value="用户id")
    private Long userinfoId;

    public Collection(Long strategyId, Long userinfoId) {
        this.strategyId = strategyId;
        this.userinfoId = userinfoId;
    }
}
