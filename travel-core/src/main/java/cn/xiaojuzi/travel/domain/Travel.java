package cn.xiaojuzi.travel.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 游记
 * author:xiaojuzi
 */
@ApiModel("游记实体类")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("travel")
public class Travel extends BaseDomain {

    public static final int STATE_NORMAL = 0;  //草稿
    public static final int STATE_WAITING = 1;  //待发布(待审核)
    public static final int STATE_RELEASE = 2;  //审核通过
    public static final int STATE_REJECT = 3;  //拒绝

    public static final int ISPUBLIC_NO = 0;
    public static final int ISPUBLIC_YES = 1;


    @ApiModelProperty(value="目的地id")
    private Long destId;

    @ApiModelProperty(value="目的地名字")
    private String destName;

    @ApiModelProperty(value="作者id")
    private Long authorId;
    @TableField(exist = false)
    @ApiModelProperty(value="作者")
    private UserInfo author;
    @ApiModelProperty(value="标题")
    private String title;
    @ApiModelProperty(value="概要")
    private String summary;
    @ApiModelProperty(value="封面")
    private String coverUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value="旅游时间")
    private Date travelTime;
    @ApiModelProperty(value="人均消费")
    private Integer avgConsume;
    @ApiModelProperty(value="旅游天数")
    private Integer day;
    @ApiModelProperty(value="和谁旅游")
    private Integer person;
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    @ApiModelProperty(value="发布时间")
    private Date releaseTime;
    @ApiModelProperty(value="最新更新时间内")
    private Date lastUpdateTime;
    @ApiModelProperty(value="是否发布")
    private Integer ispublic=ISPUBLIC_NO;
    @ApiModelProperty(value="点击阅读数")
    private Integer viewnum;
    @ApiModelProperty(value="回复数")
    private Integer replynum;
    @ApiModelProperty(value="收藏数")
    private Integer favornum;
    @ApiModelProperty(value="分享数")
    private Integer sharenum;
    @ApiModelProperty(value="点赞数")
    private Integer thumbsupnum;
    @ApiModelProperty(value="游记状态")
    private Integer state = STATE_NORMAL;
    @ApiModelProperty(value="游记内容")
    @TableField(exist = false)
    private TravelContent content;

    public String getStateDisplay(){
        if (state ==STATE_NORMAL ){
            return "编辑中";
        }else if(state ==STATE_WAITING ){
            return "待发布";
        }else if(state ==STATE_RELEASE ){
            return "已发布";
        }else if(state ==STATE_REJECT ){
            return "已拒绝";
        }
        return "";
    }

    public String getPersonDisplay(){
       if (person == 1){
           return "一个人";
       }else if (person == 2){
           return "情侣/夫妻";
       }else if (person == 3){
           return "带孩子";
       }else if (person == 4){
           return "家庭出游";
       }else if (person == 5){
           return "和朋友";
       }else if (person == 6){
           return "和同学";
       }else if (person == 7){
           return "其它";
       }
       return "其它";
    }
}