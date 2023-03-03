package cn.xiaojuzi.travel.vo;

import cn.xiaojuzi.travel.domain.StrategyCatalog;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 攻略主题VO
 * @Author xiaojuzi
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class StrategyCatalogVO {

  @ApiModelProperty(value = "名称")
  private String destName;

  @ApiModelProperty(value = "攻略主题对象")
  private List<StrategyCatalog> catalog;
}
