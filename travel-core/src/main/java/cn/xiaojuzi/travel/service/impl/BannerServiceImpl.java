package cn.xiaojuzi.travel.service.impl;

import cn.xiaojuzi.travel.domain.Banner;
import cn.xiaojuzi.travel.mapper.BannerMapper;
import cn.xiaojuzi.travel.query.BannerQuery;
import cn.xiaojuzi.travel.service.IBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* banner服务接口实现
 * author:xiaojuzi
*/
@Service
@Transactional
public class BannerServiceImpl extends ServiceImpl<BannerMapper,Banner> implements IBannerService {

    @Override
    public IPage<Banner> queryPage(BannerQuery qo) {
        IPage<Banner> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<Banner> wrapper = Wrappers.<Banner>query();
        return super.page(page, wrapper);
    }

    @Override
    public List<Banner> queryByType(int type) {
        //1:type 类型
        //2:状态正常
        //3:前5个
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type)
                .eq("state", Banner.STATE_NORMAL)
                .last("limit 5");
        return super.list(wrapper);
    }
}
