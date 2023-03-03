package cn.xiaojuzi.travel.search.service;


import cn.xiaojuzi.travel.search.domain.DestinationEs;

import java.util.List;

/**
 * author:xiaojuzi
 */
public interface IDestinationEsService {
    /** 添加
    * @param destinationEsEs
    * @return
     */
    void save(DestinationEs destinationEsEs);

    /**
     * 更新
     * @param destinationEsEs
     * @return
     */
    void update(DestinationEs destinationEsEs);

    /**
     * 查单个
     * @param id
     * @return
     */
    DestinationEs get(String id);

    /**
     * 查多个
     * @return
     */
    List<DestinationEs> list();

    /**
     * 删除
     * @param id
     */
    void delete(String id);

}
