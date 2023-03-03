package cn.xiaojuzi.travel.service;


import cn.xiaojuzi.travel.domain.Region;
import cn.xiaojuzi.travel.query.RegionQuery;
import cn.xiaojuzi.travel.util.JsonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 区域服务层接口
 * author:xiaojuzi
 */
public interface IRegionService extends IService<Region> {


    /**
     * 分页查询
     * @param qo
     * @return
     */
    Page<Region> queryPage(RegionQuery qo);

    /**
     * 修改热门状态
     * @param id
     * @param hot
     */
    void changeHotValue(Long id, int hot);

    /**
     * 热门区域
     * @return
     */
    List<Region> queryHotRegion();

    /**
     *增加地区
     * @param region
     * @return
     */
    JsonResult insert(Region region);

    /**
     *删除地区
     * @param id
     * @return
     */
    JsonResult delete(Long id);

    /**
     * 查找热门区域
     * @return
     */
    JsonResult getHotRegion();
}
