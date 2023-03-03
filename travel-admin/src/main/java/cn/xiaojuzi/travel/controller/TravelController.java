package cn.xiaojuzi.travel.controller;

import cn.xiaojuzi.travel.domain.Travel;
import cn.xiaojuzi.travel.domain.TravelContent;
import cn.xiaojuzi.travel.query.TravelQuery;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* 游记控制层
*/
/**
 * @program: tourism_platform
 * @description:游记控制层
 * @author: xiaojuzi
 * @create: 2022-02-08 14:00
 **/
@Controller
@Api(tags = "游记相关接口")
@RequestMapping("/travel")
public class TravelController {

    @Autowired
    private ITravelService travelService;

    @ApiOperation(value = "查询所有游记")
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") TravelQuery qo){
        IPage<Travel> page = travelService.queryPage(qo);
        model.addAttribute("page", page);
        return "travel/list";
    }

    @ApiOperation(value = "根据id查询游记")
    @RequestMapping("/get")
    @ResponseBody
    public Object get(Long id){
        return JsonResult.success(travelService.getById(id));
    }


    @ApiOperation(value = "根据id查询游记内容")
    @RequestMapping("/getContentById")
    @ResponseBody
    public Object getContentById(Long id){
        TravelContent content = travelService.getContent(id);
        return JsonResult.success(content.getContent());
    }


    @ApiOperation(value = "新增或者修改游记")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Travel travel){
        travelService.saveOrUpdate(travel);
        return JsonResult.success();
    }

    @ApiOperation(value = "根据id删除游记")
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Long id){
        travelService.removeById(id);
        return JsonResult.success();
    }


    @ApiOperation(value = "游记审核")
    @RequestMapping("/audit")
    @ResponseBody
    public Object audit(Long id, int state){
        travelService.audit(id, state);
        return JsonResult.success();
    }
}
