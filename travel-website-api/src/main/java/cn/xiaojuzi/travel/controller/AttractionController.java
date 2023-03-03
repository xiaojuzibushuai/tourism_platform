package cn.xiaojuzi.travel.controller;

import cn.xiaojuzi.travel.query.AttractionQueryObject;
import cn.xiaojuzi.travel.service.AttractionService;
import cn.xiaojuzi.travel.util.JsonResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: tourism_platform
 * @description:景点相关接口
 * @author: xiaojuzis
 * @create: 2022-03-07 17:34
 **/
@RestController
@Api(tags = "景点相关接口")
@RequestMapping("/attractions")
public class AttractionController {
    @Autowired
    AttractionService attractionService;

    @GetMapping("/list")
    public JsonResult list(AttractionQueryObject qo){
        return attractionService.listForPage(qo);
    }

    @GetMapping("/detail")
    public JsonResult detail(Long id){
        return attractionService.detail(id);
    }
}
