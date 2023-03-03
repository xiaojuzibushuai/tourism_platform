package cn.xiaojuzi.travel.service;


import cn.xiaojuzi.travel.domain.Destination;
import cn.xiaojuzi.travel.query.DestinationQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 目的地服务层接口
 * author:xiaojuzi
 */
public interface IDestinationService extends IService<Destination> {

    /**
     *查询所有目的地
     * @param qo
     * @return
     */
        List<Destination> listAll(DestinationQuery qo);

    /**
     * 新增目的地
     * @param destination
     */
    void insert(Destination destination);

    /**
     * 根据id删除
     * @param id
     */
        void  deleteById(Long id);



    /**
     * 查询指定区域id下的挂载目的地集合
     * @param rid
     * @return
     */
    List<Destination> queryByRegionId(Long rid);

    /**
     * 查询指定区域id下的挂载目的地集合
     * @param rid
     * @return
     */
    List<Destination> queryByRegionIdForApi(Long rid);


    /**
     * 分页查询
     * @param qo
     * @return
     */
    Page<Destination> queryPage(DestinationQuery qo);

    /**
     * 吐司查询
     * @param destId
     * @return
     */
    List<Destination> queryToasts(Long destId);


    /**
     * 通过name查询目的地
     * @param name
     * @return
     */
    Destination queryByName(String name);
}
