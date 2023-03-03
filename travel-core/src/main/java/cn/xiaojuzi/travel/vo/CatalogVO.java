package cn.xiaojuzi.travel.vo;

import cn.xiaojuzi.travel.domain.StrategyCatalog;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CatalogVO {
    private String destName;
    private List<StrategyCatalog> catalogList;
}
