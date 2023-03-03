package cn.xiaojuzi.travel.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 攻略
 * author:xiaojuzi
 */
@ApiModel(value="攻略实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("strategy")
public class Strategy extends BaseDomain {
    public static final int  ABROAD_NO = 0;  //国内
    public static final int  ABROAD_YES = 1;  //国外

    public static final int STATE_NORMAL = 0;  //带发布
    public static final int STATE_PUBLISH = 1; //发布


    @ApiModelProperty(value="关联的目的地的id")
    private Long destId;

    @ApiModelProperty(value="关联的目的地的名称")
    private String destName;

    @ApiModelProperty(value="关联主题的id")
    private Long themeId;

    @ApiModelProperty(value="关联主题的名称")
    private String themeName;

    @ApiModelProperty(value="关联的分类的id")
    private Long catalogId;

    @ApiModelProperty(value="关联的分类的名称")
    private String catalogName;

    @ApiModelProperty(value="标题")
    private String title;

    @ApiModelProperty(value="副标题")
    private String subTitle;

    @ApiModelProperty(value="内容摘要")
    private String summary;

    @ApiModelProperty(value="封面")
    private String coverUrl;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty(value="是否是国外")
    private Integer isabroad = ABROAD_NO;

    @TableField("viewnum")
    @ApiModelProperty(value="点击数")
    private Integer viewNum;

    @TableField("replynum")
    @ApiModelProperty(value="攻略评论数")
    private Integer replyNum;

    @TableField("favornum")
    @ApiModelProperty(value="收藏数")
    private Integer favorNum;

    @TableField("sharenum")
    @ApiModelProperty(value="分享数")
    private Integer shareNum;

    @TableField("thumbsupnum")
    @ApiModelProperty(value="点赞个数")
    private Integer thumbsupNum;

    @ApiModelProperty(value="发布状态")
    private Integer state = STATE_PUBLISH;

    @TableField(exist = false)
    @ApiModelProperty(value="攻略内容")
    private StrategyContent content;

    public String getStateDisplay(){
        return state == STATE_PUBLISH ? "发布" :"待发布";
    }
}
