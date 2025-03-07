package cn.xiaojuzi.travel.controller;

import cn.xiaojuzi.travel.query.DestinationQuery;
import cn.xiaojuzi.travel.service.*;
import cn.xiaojuzi.travel.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: tourism_platform
 * @description:前端目的地相关接口
 * @author: xiaojuzi
 * @create: 2022-03-08 10:45
 **/
@RestController
@Api(tags = "前端目的地相关接口")
@RequestMapping("/destinations")
public class DestinationController {

    @Autowired
    private IRegionService regionService;
    @Autowired
    private IDestinationService destinationService;
    @Autowired
    private IStrategyCatalogService strategyCatalogService;

    @Autowired
    private ITravelService travelService;

    @Autowired
    private IStrategyService strategyService;

    @GetMapping("/hotRegion")
    public Object hotRegion(){
        return JsonResult.success(regionService.queryHotRegion());
    }


    @GetMapping("/search")
    public Object search(Long regionId){
        //查询某个区域下挂载所有目的地
        return JsonResult.success(destinationService.queryByRegionIdForApi(regionId));
    }
    @GetMapping("/toasts")
    public Object toasts(Long destId){
        return JsonResult.success(destinationService.queryToasts(destId));
    }


    //查分类
    @GetMapping("/catalogs")
    public Object catalogs(Long destId){
        return JsonResult.success(strategyCatalogService.queryByDestId(destId));
    }

    @GetMapping("/strategies/viewnumTop3")
    public Object strategyViewnumTop3(Long destId){
        return JsonResult.success(strategyService.queryViewnumTop3(destId));
    }

    @GetMapping("/travels/viewnumTop3")
    public Object travelViewnumTop3(Long destId){
        return JsonResult.success(travelService.queryViewnumTop3(destId));
    }

//    @ApiOperation(value = "查询所有目的地")
//    @PostMapping("/list")
//    public String list (Model model, @ModelAttribute("qo") DestinationQuery qo){
//        //toasts
//        model.addAttribute("toasts", destinationService.queryToasts(qo.getParentId()));
//        //page--MP
//        model.addAttribute("page", destinationService.queryPage(qo));
//        return "destination/list";
//    }

}
