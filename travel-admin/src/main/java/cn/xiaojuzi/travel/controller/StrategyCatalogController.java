package cn.xiaojuzi.travel.controller;

import cn.xiaojuzi.travel.domain.StrategyCatalog;
import cn.xiaojuzi.travel.query.StrategyCatalogQuery;
import cn.xiaojuzi.travel.service.IDestinationService;
import cn.xiaojuzi.travel.service.IStrategyCatalogService;
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
 * @description:攻略分类控制层
 * @author: xiaojuzi
 * @create: 2022-02-08 12:11
 **/
@Controller
@Api(tags = "攻略分类相关接口")
@RequestMapping("/strategyCatalog")
public class StrategyCatalogController {

    @Autowired
    private IStrategyCatalogService strategyCatalogService;
    @Autowired
    private IDestinationService destinationService;

    @ApiOperation(value = "展示攻略分类")
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") StrategyCatalogQuery qo){
        IPage<StrategyCatalog> page = strategyCatalogService.queryPage(qo);
        model.addAttribute("page", page);

        model.addAttribute("dests",destinationService.list());

        return "strategyCatalog/list";
    }

    @ApiOperation(value = "通过id获取攻略分类")
    @RequestMapping("/get")
    @ResponseBody
    public Object get(Long id){
        return JsonResult.success(strategyCatalogService.getById(id));
    }

    @ApiOperation(value = "修改攻略分类")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StrategyCatalog strategyCatalog){
        strategyCatalog.setDestName(destinationService.getById(strategyCatalog.getDestId()).getName());
        strategyCatalogService.saveOrUpdate(strategyCatalog);
        return JsonResult.success();
    }

    @ApiOperation(value = "根据id删除攻略分类")
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Long id){
        strategyCatalogService.removeById(id);
        return JsonResult.success();
    }
}
