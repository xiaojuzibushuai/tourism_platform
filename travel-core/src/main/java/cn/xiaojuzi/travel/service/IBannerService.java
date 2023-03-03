package cn.xiaojuzi.travel.service;

import cn.xiaojuzi.travel.domain.Banner;
import cn.xiaojuzi.travel.query.BannerQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * banner服务接口
 * author:xiaojuzi
 */
public interface IBannerService extends IService<Banner>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<Banner> queryPage(BannerQuery qo);


    /**
     * 通过类型查询
     * @param type
     * @return
     */
    List<Banner> queryByType(int type);
}
