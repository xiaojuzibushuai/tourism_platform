package cn.xiaojuzi.travel.controller;

import cn.xiaojuzi.travel.domain.StrategyTheme;
import cn.xiaojuzi.travel.query.StrategyThemeQuery;
import cn.xiaojuzi.travel.service.IStrategyThemeService;
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
import org.springframework.web.bind.annotation.RestController;


/**
 * @program: tourism_platform
 * @description:攻略主题控制层
 * @author: xiaojuzi
 * @create: 2022-02-08 13:21
 **/
@Controller
@Api(tags = "攻略主题相关接口")
@RequestMapping("/strategyTheme")
public class StrategyThemeController {

    @Autowired
    private IStrategyThemeService strategyThemeService;

    @ApiOperation(value = "攻略主题的展示")
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") StrategyThemeQuery qo){
        IPage<StrategyTheme> page = strategyThemeService.queryPage(qo);
        model.addAttribute("page", page);
        return "strategyTheme/list";
    }

    @ApiOperation(value = "根据id查询攻略主题")
    @RequestMapping("/get")
    @ResponseBody
    public Object get(Long id){
        return JsonResult.success(strategyThemeService.getById(id));
    }

    @ApiOperation(value = "攻略主题的新增或者修改")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StrategyTheme strategyTheme){
        strategyThemeService.saveOrUpdate(strategyTheme);
        return JsonResult.success();
    }

    @ApiOperation(value = "根据id删除攻略主题")
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Long id){
        strategyThemeService.removeById(id);
        return JsonResult.success();
    }
}
