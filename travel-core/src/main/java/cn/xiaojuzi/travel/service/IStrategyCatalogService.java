package cn.xiaojuzi.travel.service;

import cn.xiaojuzi.travel.domain.StrategyCatalog;
import cn.xiaojuzi.travel.query.StrategyCatalogQuery;
import cn.xiaojuzi.travel.vo.CatalogVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 攻略分类服务接口
 * author:xiaojuzi
 */
public interface IStrategyCatalogService extends IService<StrategyCatalog>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<StrategyCatalog> queryPage(StrategyCatalogQuery qo);

    /**
     * 攻略分类封装对象
     * @return
     */
    List<CatalogVO> queryGroupCatalog();

    /**
     * 查询指定目的地下攻略分类
     * @param destId
     * @return
     */
    List<StrategyCatalog> queryByDestId(Long destId);
}
