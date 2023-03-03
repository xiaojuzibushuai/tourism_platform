package cn.xiaojuzi.travel.controller;

import cn.xiaojuzi.travel.domain.Strategy;
import cn.xiaojuzi.travel.domain.StrategyContent;
import cn.xiaojuzi.travel.query.StrategyQuery;
import cn.xiaojuzi.travel.service.IStrategyCatalogService;
import cn.xiaojuzi.travel.service.IStrategyService;
import cn.xiaojuzi.travel.service.IStrategyThemeService;
import cn.xiaojuzi.travel.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * @program: tourism_platform
 * @description:攻略明细控制层
 * @author: xiaojuzi
 * @create: 2022-02-08 12:16
 **/
@Controller
@Api(tags = "攻略明细相关接口")
@RequestMapping("/strategy")
public class StrategyController {

    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private IStrategyThemeService strategyThemeService;
    @Autowired
    private IStrategyCatalogService strategyCatalogService;

    @RequestMapping("/list")
    @ApiOperation(value = "查询所有攻略明细")
    public String list(Model model, @ModelAttribute("qo") StrategyQuery qo){
        IPage<Strategy> page = strategyService.queryPage(qo);
        model.addAttribute("page", page);
        return "strategy/list";
    }


    @ApiOperation(value = "攻略跳转到增加页或者编辑页")
    @RequestMapping("/input")
    public String input(Model model, Long id){
        if (id != null){
            //strategy  编辑时才有
            Strategy strategy = strategyService.getById(id);
            //内容
            StrategyContent content = strategyService.getContent(id);
            strategy.setContent(content);
            model.addAttribute("strategy", strategy);
        }
        //catalogs
        model.addAttribute("catalogs", strategyCatalogService.queryGroupCatalog());
        //themes
        model.addAttribute("themes", strategyThemeService.list());

        return "strategy/input";
    }



    @ApiOperation(value = "根据id查询攻略明细")
    @RequestMapping("/get")
    @ResponseBody
    public Object get(Long id){
        return JsonResult.success(strategyService.getById(id));
    }

    @ApiOperation(value = "攻略明细编辑或者新增")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Strategy strategy){
        strategyService.saveOrUpdate(strategy);
        return JsonResult.success();
    }

    @ApiOperation(value = "根据id删除攻略明细")
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Long id){
        strategyService.removeById(id);
        return JsonResult.success();
    }

    @RequestMapping("/info")
    public String info(){

        return"strategy/info";
    }

    @RequestMapping("/changeState")
    @ApiOperation(value = "修改状态")
    @ResponseBody
    public JsonResult changeState(Integer state,Long id){
        return strategyService.updateState(state,id);
    }



}
