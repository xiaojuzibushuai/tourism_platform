package cn.xiaojuzi.travel.search.service;


import cn.xiaojuzi.travel.search.domain.TravelEs;

import java.util.List;

/**
 * xiaojuzi
 */
public interface ITravelEsService {
    /** 添加
    * @param travelEsEs
    * @return
     */
    void save(TravelEs travelEsEs);

    /**
     * 更新
     * @param travelEsEs
     * @return
     */
    void update(TravelEs travelEsEs);

    /**
     * 查单个
     * @param id
     * @return
     */
    TravelEs get(String id);

    /**
     * 查多个
     * @return
     */
    List<TravelEs> list();

    /**
     * 删除
     * @param id
     */
    void delete(String id);

}
