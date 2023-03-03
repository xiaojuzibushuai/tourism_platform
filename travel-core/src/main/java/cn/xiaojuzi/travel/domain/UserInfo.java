package cn.xiaojuzi.travel.domain;

import cn.xiaojuzi.travel.constant.SystemConstant;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 用户信息
 * author:xiaojuzi
 */
@ApiModel(description = "用户信息实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("userinfo")
public class UserInfo extends BaseDomain{

    public static final int GENDER_SECRET = 0; //保密
    public static final int GENDER_MALE = 1;   //男
    public static final int GENDER_FEMALE = 2;  //女
    public static final int STATE_NORMAL = 0;  //正常
    public static final int STATE_DISABLE = 1;  //冻结

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @JsonIgnore
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别")
    private Integer gender = SystemConstant.GENDER_SECRET;

    @ApiModelProperty(value = "用户级别")
    private Integer level = 0;

    @ApiModelProperty(value = "所在城市")
    private String city;

    @ApiModelProperty(value = "头像")
    private String headImgUrl;

    @ApiModelProperty(value = "个性签名")
    private String info;

    @ApiModelProperty(value = "状态")
    private Integer state = SystemConstant.STATE_NORMAL;

    public UserInfo(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

}
