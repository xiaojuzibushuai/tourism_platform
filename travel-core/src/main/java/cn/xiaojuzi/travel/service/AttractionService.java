package cn.xiaojuzi.travel.service;

import cn.xiaojuzi.travel.domain.Attraction;
import cn.xiaojuzi.travel.query.AttractionQueryObject;
import cn.xiaojuzi.travel.util.JsonResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @program: tourism_platform
 * @description:景点业务类
 * @author: xiaojuzi
 * @create: 2022-03-08 10:02
 **/
public interface AttractionService extends IService<Attraction> {

    JsonResult listForPage(AttractionQueryObject qo);

    JsonResult detail(Long id);
}
