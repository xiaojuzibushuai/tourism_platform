package cn.xiaojuzi.travel.controller;

import cn.xiaojuzi.travel.domain.Destination;
import cn.xiaojuzi.travel.query.DestinationQuery;
import cn.xiaojuzi.travel.service.IDestinationService;
import cn.xiaojuzi.travel.util.JsonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @program: tourism_platform
 * @description:目的地控制层
 * @author: xiaojuzi
 * @create: 2022-02-08 11:26
 **/
@Controller
@Api(tags = "目的地相关接口")
@RequestMapping("/destination")
public class DestinationController {

    @Autowired
    private IDestinationService destinationService;


    @ApiOperation(value = "查询所有目的地")
    @RequestMapping("/list")
    public String list (Model model, @ModelAttribute("qo") DestinationQuery qo){
        //toasts
        model.addAttribute("toasts", destinationService.queryToasts(qo.getParentId()));
        //page--MP
        model.addAttribute("page", destinationService.queryPage(qo));
        return "destination/list";
    }


    @ApiOperation(value = "修改目的地")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Destination destination){
        destinationService.saveOrUpdate(destination);
        return JsonResult.success();
    }



    @ApiOperation(value = "删除目的地")
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Long id){
        destinationService.removeById(id);
        return JsonResult.success();
    }



}
