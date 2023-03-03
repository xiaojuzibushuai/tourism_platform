package cn.xiaojuzi.travel.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Author xiaojuzi
 */
@Data
public class UserInfoLoginVO {

  @ApiModelProperty(value = "手机")
  private String phone;

  @ApiModelProperty(value = "密码")
  private String password;
}
