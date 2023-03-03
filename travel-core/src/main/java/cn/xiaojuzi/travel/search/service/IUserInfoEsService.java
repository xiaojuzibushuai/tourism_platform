package cn.xiaojuzi.travel.search.service;


import cn.xiaojuzi.travel.search.domain.UserInfoEs;

import java.util.List;

/**
 * xiaojuzi
 */
public interface IUserInfoEsService {
    /** 添加
    * @param userInfoEs
    * @return
     */
    void save(UserInfoEs userInfoEs);

    /**
     * 更新
     * @param userInfoEs
     * @return
     */
    void update(UserInfoEs userInfoEs);

    /**
     * 查单个
     * @param id
     * @return
     */
    UserInfoEs get(String id);

    /**
     * 查多个
     * @return
     */
    List<UserInfoEs> list();

    /**
     * 删除
     * @param id
     */
    void delete(String id);

}
