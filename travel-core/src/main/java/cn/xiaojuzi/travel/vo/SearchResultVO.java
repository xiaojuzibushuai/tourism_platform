package cn.xiaojuzi.travel.vo;


import cn.xiaojuzi.travel.domain.Destination;
import cn.xiaojuzi.travel.domain.Strategy;
import cn.xiaojuzi.travel.domain.Travel;
import cn.xiaojuzi.travel.domain.UserInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class SearchResultVO implements Serializable {


    private Long total = 0L;

    private List<Strategy> strategys = new ArrayList<>();
    private List<Travel> travels = new ArrayList<>();
    private List<UserInfo> users = new ArrayList<>();
    private List<Destination> dests = new ArrayList<>();

}
