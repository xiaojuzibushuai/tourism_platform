package cn.xiaojuzi.travel.service.impl;

import cn.xiaojuzi.travel.domain.Strategy;
import cn.xiaojuzi.travel.domain.StrategyCatalog;
import cn.xiaojuzi.travel.mapper.StrategyCatalogMapper;
import cn.xiaojuzi.travel.query.StrategyCatalogQuery;
import cn.xiaojuzi.travel.service.IStrategyCatalogService;
import cn.xiaojuzi.travel.service.IStrategyService;
import cn.xiaojuzi.travel.vo.CatalogVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* 攻略分类服务接口实现
 * author:xiaojuzi
*/
@Service
@Transactional
public class StrategyCatalogServiceImpl extends ServiceImpl<StrategyCatalogMapper, StrategyCatalog> implements IStrategyCatalogService {

    @Autowired
    private IStrategyService strategyService;

    @Override
    public IPage<StrategyCatalog> queryPage(StrategyCatalogQuery qo) {
        IPage<StrategyCatalog> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<StrategyCatalog> wrapper = Wrappers.<StrategyCatalog>query();
        return super.page(page, wrapper);
    }

    @Override
    public List<CatalogVO> queryGroupCatalog() {
        List<CatalogVO> vos = new ArrayList<>();
        //1:查询数据
        QueryWrapper<StrategyCatalog> wrapper = new QueryWrapper<>();
        wrapper.select("dest_name, GROUP_CONCAT(name) names, GROUP_CONCAT(id) ids ")
               .groupBy("dest_name");
        List<Map<String, Object>> list = super.listMaps(wrapper);
        //2：封装vo对象
        //dest_name  names  ids  ----> vo
        for (Map<String, Object> map : list) {
            CatalogVO vo = new CatalogVO();
            vo.setDestName(map.get("dest_name").toString());  //目的地名字
            String names = map.get("names").toString();
            String ids = map.get("ids").toString();

            List<StrategyCatalog> catalogList = this.parseCatalog(names, ids);
            vo.setCatalogList(catalogList);
            vos.add(vo);
        }
        return vos;
    }

    private List<StrategyCatalog> parseCatalog(String names, String ids) {
        List<StrategyCatalog> list = new ArrayList<>();
        String[] ns = names.split(","); //截取name
        String[] is = ids.split(",");//截取id
        if(ns.length > 0){
            for (int i = 0; i <ns.length; i++) {
                String name = ns[i];
                String id = is[i];
                if(!StringUtils.hasLength(name)){
                    continue;
                }
                StrategyCatalog catalog = new StrategyCatalog();
                catalog.setName(name);
                catalog.setId(Long.parseLong(id));
                list.add(catalog);
            }
        }
        return list;
    }

    @Override
    public List<StrategyCatalog> queryByDestId(Long destId) {

        //查询指定目的地下攻略分类集合
        QueryWrapper<StrategyCatalog> wrapper = new QueryWrapper<>();
        wrapper.eq("dest_id", destId);
        List<StrategyCatalog> list = super.list(wrapper);

        QueryWrapper<Strategy> stwrapper = new QueryWrapper<Strategy>();
        //每个分类下面的攻略集合
        for (StrategyCatalog catalog : list) {
            stwrapper.clear();
            stwrapper.eq("catalog_id", catalog.getId());
            List<Strategy> sts  = strategyService.list(stwrapper);
            catalog.setStrategies(sts);
        }
        return list;
    }
}
