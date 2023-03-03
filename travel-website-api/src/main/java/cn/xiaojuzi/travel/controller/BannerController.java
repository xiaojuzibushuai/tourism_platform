package cn.xiaojuzi.travel.controller;

import cn.xiaojuzi.travel.domain.Banner;
import cn.xiaojuzi.travel.service.IBannerService;
import cn.xiaojuzi.travel.util.JsonResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: tourism_platform
 * @description:广告相关接口
 * @author: xiaojuzi
 * @create: 2022-03-07 17:34
 **/
@RestController
@Api(tags = "广告相关接口")
@RequestMapping("/banners")
public class BannerController {

    @Autowired
    private IBannerService bannerService;



    @GetMapping("/travel")
    public Object travel(){
        return JsonResult.success(bannerService.queryByType(Banner.TYPE_TRAVEL));
    }

    @GetMapping("/strategy")
    public Object strategy(){
        return JsonResult.success(bannerService.queryByType(Banner.TYPE_STRATEGY));
    }

}
