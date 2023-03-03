package cn.xiaojuzi.travel.service.impl;

import cn.xiaojuzi.travel.domain.Attraction;
import cn.xiaojuzi.travel.mapper.AttractionMapper;
import cn.xiaojuzi.travel.query.AttractionQueryObject;
import cn.xiaojuzi.travel.service.AttractionService;
import cn.xiaojuzi.travel.util.JsonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: tourism_platform
 * @description:景点业务类
 * @author: xiaojuzi
 * @create: 2022-03-08 10:04
 **/
@Service
@Transactional
public class AttractionServiceImpl extends ServiceImpl<AttractionMapper, Attraction> implements AttractionService {
    @Override
    public JsonResult listForPage(AttractionQueryObject qo) {
        Page<Attraction> page = new Page<>(qo.getCurrentPage(),qo.getPageSize());
        Page<Attraction> attractionPage = super.page(page);
        return JsonResult.success(attractionPage);
    }

    @Override
    public JsonResult detail(Long id) {
        Attraction attraction = super.getById(id);
        return JsonResult.success(attraction);
    }
}
