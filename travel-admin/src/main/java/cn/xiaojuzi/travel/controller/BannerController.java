package cn.xiaojuzi.travel.controller;

import cn.xiaojuzi.travel.domain.Banner;
import cn.xiaojuzi.travel.domain.Strategy;
import cn.xiaojuzi.travel.domain.Travel;
import cn.xiaojuzi.travel.query.BannerQuery;
import cn.xiaojuzi.travel.service.IBannerService;
import cn.xiaojuzi.travel.service.IStrategyService;
import cn.xiaojuzi.travel.service.ITravelService;
import cn.xiaojuzi.travel.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: tourism_platform
 * @description:banner控制层
 * @author: xiaojuzi
 * @create: 2022-02-08 11:16
 **/
@Controller
@Api(tags = "banner相关接口")
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private IBannerService bannerService;
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private ITravelService travelService;

    @ApiOperation(value = "查询所有banner")
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") BannerQuery qo){
        IPage<Banner> page = bannerService.queryPage(qo);
        model.addAttribute("page", page);
        return "banner/list";
    }

    @ApiOperation(value = "根据id查询banner")
    @RequestMapping("/get")
    @ResponseBody
    public Object get(Long id){
        return JsonResult.success(bannerService.getById(id));
    }

    @ApiOperation(value = "修改banner")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Banner banner){
        bannerService.saveOrUpdate(banner);
        return JsonResult.success();
    }

    @ApiOperation(value = "根据id删除banner")
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Long id){
        bannerService.removeById(id);
        return JsonResult.success();
    }

    @ApiOperation(value = "查询所有类型")
    @RequestMapping("/getAllType")
    @ResponseBody
    public Object getAllType(){

        List<Strategy> sts = strategyService.list();
        List<Travel> ts = travelService.list();

        Map<String, Object> map = new HashMap<>();

        map.put("sts",sts);
        map.put("ts",ts);
        return JsonResult.success(map);
    }
}
