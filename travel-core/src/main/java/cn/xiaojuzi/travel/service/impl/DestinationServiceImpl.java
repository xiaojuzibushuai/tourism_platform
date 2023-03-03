package cn.xiaojuzi.travel.service.impl;


import cn.xiaojuzi.travel.domain.Destination;
import cn.xiaojuzi.travel.domain.Region;
import cn.xiaojuzi.travel.mapper.DestinationMapper;
import cn.xiaojuzi.travel.query.DestinationQuery;
import cn.xiaojuzi.travel.service.IDestinationService;
import cn.xiaojuzi.travel.service.IRegionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * xiaojuzi
 */
@Service
@Transactional
public class DestinationServiceImpl extends ServiceImpl<DestinationMapper, Destination> implements IDestinationService {

    @Autowired
    private IRegionService regionService;

    @Autowired
    private DestinationMapper destinationMapper;


    private QueryWrapper<Destination> wrapper = new QueryWrapper<Destination>();
    private QueryWrapper<Destination> getWrapper(){
        wrapper.clear();
        return wrapper;
    }

    @Override
    public List<Destination> listAll(DestinationQuery qo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(qo.getKeyword()!=null,"name",qo.getKeyword());
        return super.list();
    }

    @Override
    public void insert(Destination destination) {
        super.save(destination);
    }

    @Override
    public void deleteById(Long id) {
        super.removeById(id);
    }

    @Override
    public List<Destination> queryByRegionId(Long rid) {

        //1:查询区域对象得到目的地ids集合
        Region region = regionService.getById(rid);
        String refIds = region.getRefIds();
        //2:通过ids集合查询目的地列表

        //wrapper.inSql("id", refIds.substring(0, refIds.length()-1));  //in(1,2,3)
        getWrapper().in("id",region.parseRefIds());  //in(1,2,3)
        return super.list(wrapper);
    }

    @Override
    public List<Destination> queryByRegionIdForApi(Long rid) {

        //第一层： 挂载目的地集合
        //如果rid = -1 查询中国所有省份
        List<Destination> list = null;
        if(rid == -1){
            //查询所有省份
            getWrapper().eq("parent_name", "中国");
            list = super.list(wrapper);
        }else{
            list = this.queryByRegionId(rid);
        }
        //第二层：子目的地集合
        for (Destination dest : list) {
            //查询儿子
            //wrapper.clear();  //请空之前拼接所有条件/其他sql片段
            getWrapper().eq("parent_id", dest.getId())
                    .last("limit 5");
            List<Destination> children = super.list(wrapper);
            dest.setChildren(children);
        }
        return list;
    }

    @Override
    public Page<Destination> queryPage(DestinationQuery qo) {
        getWrapper().like(StringUtils.hasLength(qo.getKeyword()), "name", qo.getKeyword())
                .isNull(qo.getParentId() == null, "parent_id")
                .eq(qo.getParentId() != null, "parent_id", qo.getParentId());

        Page<Destination> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        return super.page(page, wrapper);
    }

    //当前id是广州id：     根>>中国>>广东>>广州
    @Override
    public List<Destination> queryToasts(Long destId) {
        List<Destination> list = new ArrayList<>();

        //1:while循环方式
        //2:递归操作方式
        this.createToast(list, destId);
        Collections.reverse(list);  //ABC  CBA

        return list;
    }

    //当前id是广州id：     根>>中国>>广东>>广州
    private void createToast(List<Destination> list, Long destId){
        if(destId == null){
            return;
        }
        Destination dest = super.getById(destId);
        list.add(dest);

        if(dest.getParentId() != null){
            this.createToast(list, dest.getParentId());
        }
    }

    @Override
    public Destination queryByName(String name) {
        return super.getOne(Wrappers.<Destination>query().eq("name",name));
    }
}
