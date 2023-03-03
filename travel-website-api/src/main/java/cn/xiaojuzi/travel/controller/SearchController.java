package cn.xiaojuzi.travel.controller;

import cn.xiaojuzi.travel.domain.Destination;
import cn.xiaojuzi.travel.domain.Strategy;
import cn.xiaojuzi.travel.domain.Travel;
import cn.xiaojuzi.travel.domain.UserInfo;
import cn.xiaojuzi.travel.search.domain.DestinationEs;
import cn.xiaojuzi.travel.search.domain.StrategyEs;
import cn.xiaojuzi.travel.search.domain.TravelEs;
import cn.xiaojuzi.travel.search.domain.UserInfoEs;
import cn.xiaojuzi.travel.search.query.SearchQueryObject;
import cn.xiaojuzi.travel.search.service.ISearchService;
import cn.xiaojuzi.travel.search.vo.SearchResultVO;
import cn.xiaojuzi.travel.service.IDestinationService;
import cn.xiaojuzi.travel.service.IStrategyService;
import cn.xiaojuzi.travel.service.ITravelService;
import cn.xiaojuzi.travel.service.IUserInfoService;
import cn.xiaojuzi.travel.util.JsonResult;
import cn.xiaojuzi.travel.util.ParamMap;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * @program: tourism_platform
 * @description:
 * @author: xiaojuzi
 * @create: 2022-03-08 10:45
 **/
@RestController
@Api(tags = "前端搜索相关接口")
public class SearchController {


    @Autowired
    private IDestinationService destinationService;
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private ITravelService travelService;

    @Autowired
    private ISearchService searchService;



    @GetMapping("/q")
    public Object search(SearchQueryObject qo) throws UnsupportedEncodingException {
        //URL解码
        String kw = URLDecoder.decode(qo.getKeyword(), "UTF-8");
        qo.setKeyword(kw);

        switch (qo.getType()){
            case SearchQueryObject.TYPE_DEST:return this.searchDest(qo);
            case SearchQueryObject.TYPE_STRATEGY:return this.searchStrategy(qo);
            case SearchQueryObject.TYPE_TRAVEL:return this.searchTravel(qo);
            case SearchQueryObject.TYPE_USER:return this.searchUser(qo);
            default: return this.searchAll(qo);
        }
    }

    //查目的地
    private Object searchDest(SearchQueryObject qo) {

        //查询mysql 找传入key是不是一个目的地
        Destination dest = destinationService.queryByName(qo.getKeyword());
        SearchResultVO vo = new SearchResultVO();
        if(dest != null){
            //如果是， 查询该目的地下所有攻略， 游记， 用户
            List<Strategy> sts = strategyService.queryByDestId(dest.getId());
            List<Travel> ts = travelService.queryByDestId(dest.getId());
            List<UserInfo> us = userInfoService.queryByCity(dest.getName());

            vo.setStrategys(sts);
            vo.setTravels(ts);
            vo.setUsers(us);
            vo.setTotal(sts.size() + ts.size() + us.size() + 0L);
        }
        //返回结果
        return JsonResult.success(ParamMap.newInstance()
                .put("result", vo)
                .put("dest", dest)
                .put("qo", qo));
    }
    //查攻略
    private Object searchStrategy(SearchQueryObject qo) {
        return JsonResult.success(ParamMap.newInstance().put("page", this.createStrategyPage(qo)).put("qo", qo));
    }
    //查游记
    private Object searchTravel(SearchQueryObject qo) {
        return JsonResult.success(ParamMap.newInstance().put("page", this.createTravelPage(qo)).put("qo", qo));
    }

    //查用户
    private Object searchUser(SearchQueryObject qo) {
        return JsonResult.success(ParamMap.newInstance().put("page", this.createUserInfoPage(qo)).put("qo", qo));
    }

    //查所有
    private Object searchAll(SearchQueryObject qo) {
        SearchResultVO vo = new SearchResultVO();
        Page<Strategy> sts = this.createStrategyPage(qo);
        vo.setStrategys(sts.getContent());

        Page<Travel> ts = this.createTravelPage(qo);
        vo.setTravels(ts.getContent());

        Page<UserInfo> us = this.createUserInfoPage(qo);
        vo.setUsers(us.getContent());

        Page<Destination> ds = this.createDestinationPage(qo);
        vo.setDests(ds.getContent());
        vo.setTotal(sts.getTotalElements() + ts.getTotalElements() + us.getTotalElements() + ds.getTotalElements());

        return JsonResult.success(ParamMap.newInstance().put("result", vo).put("qo", qo));
    }


    private Page<Strategy> createStrategyPage(SearchQueryObject qo){
        return searchService.searchWithHighlight(StrategyEs.INDEX_NAME, Strategy.class,
                qo,"title", "subTitle", "summary"
        );
    }

    private Page<Travel> createTravelPage(SearchQueryObject qo){
        Page<Travel> ts =searchService.searchWithHighlight(TravelEs.INDEX_NAME, Travel.class,
                qo,"title", "summary"
        );

        for (Travel t : ts) {
            t.setAuthor(userInfoService.getById(t.getAuthorId()));
        }

        return ts;
    }

    private Page<UserInfo> createUserInfoPage(SearchQueryObject qo){
        return searchService.searchWithHighlight(UserInfoEs.INDEX_NAME, UserInfo.class,
                qo,"info", "city"
        );
    }

    private Page<Destination> createDestinationPage(SearchQueryObject qo){
        return  searchService.searchWithHighlight(DestinationEs.INDEX_NAME, Destination.class,
                qo,"name", "info"
        );
    }

}
