package cn.xiaojuzi.travel.service.impl;


import cn.xiaojuzi.travel.domain.Region;
import cn.xiaojuzi.travel.mapper.RegionMapper;
import cn.xiaojuzi.travel.query.RegionQuery;
import cn.xiaojuzi.travel.service.IRegionService;
import cn.xiaojuzi.travel.util.JsonResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * author:xiaojuzi
 */
@Service
@Transactional
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {

    @Override
    public Page<Region> queryPage(RegionQuery qo) {
        QueryWrapper<Region> wrapper = new QueryWrapper<>();
        Page<Region> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        return super.page(page, wrapper);
    }
    @Override
    public void changeHotValue(Long id, int hot) {
        super.update(new UpdateWrapper<Region>()
                .eq("id", id).set("ishot", hot));
    }

    @Override
    public List<Region> queryHotRegion() {
        QueryWrapper<Region> wrapper = new QueryWrapper<>();
        //1：状态为热门
        wrapper.eq("ishot", Region.STATE_HOT)
                .orderByAsc("seq");
        //2: 排序
        return super.list(wrapper);
    }

    @Override
    public JsonResult insert(Region region) {
        super.save(region);
        return JsonResult.success();
    }

    @Override
    public JsonResult delete(Long id) {
        super.removeById(id);
        return JsonResult.success();
    }

    @Override
    public JsonResult getHotRegion() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("ishot",true);
        return JsonResult.success(super.list(queryWrapper));
    }
}
