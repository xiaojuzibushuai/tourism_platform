package cn.xiaojuzi.travel.controller;

import cn.xiaojuzi.travel.domain.Destination;
import cn.xiaojuzi.travel.domain.Region;
import cn.xiaojuzi.travel.query.RegionQuery;
import cn.xiaojuzi.travel.service.IDestinationService;
import cn.xiaojuzi.travel.service.IRegionService;
import cn.xiaojuzi.travel.util.JsonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: tourism_platform
 * @description:区域控制层
 * @author: xiaojuzi
 * @create: 2022-02-08 11:46
 **/
@Controller
@Api(tags = "区域相关接口")
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private IDestinationService destinationService;

    @Autowired
    private IRegionService regionService;



    @ApiOperation(value = "查询所有热门")
    @RequestMapping("/list")
    public String list (Model model, @ModelAttribute("qo") RegionQuery qo){
        //dests
        List<Destination> dests = destinationService.list();
        model.addAttribute("dests", dests);

        //page--MP
        Page<Region> page = regionService.queryPage(qo);
        model.addAttribute("page", page);

        return "region/list";
    }



    @ApiOperation(value = "增加或者修改")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Region region){
        regionService.saveOrUpdate(region);
        return JsonResult.success();
    }


    @ApiOperation(value = "修改为热门或者是普通")
    @RequestMapping("/changeHotValue")
    @ResponseBody
    public Object changeHotValue(Long id, int hot){
        regionService.changeHotValue(id, hot);
        return JsonResult.success();
    }

    @ApiOperation(value = "删除地区")
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Long id){
        regionService.removeById(id);
        return JsonResult.success();
    }

    @ApiOperation(value = "通过区域id获取目的地")
    @RequestMapping("/getDestByRegionId")
    @ResponseBody
    public Object getDestByRegionId(Long rid){
        List<Destination> dests = destinationService.queryByRegionId(rid);
        return dests;
    }


}
