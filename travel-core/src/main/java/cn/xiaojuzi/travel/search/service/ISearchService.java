package cn.xiaojuzi.travel.search.service;

import cn.xiaojuzi.travel.search.query.SearchQueryObject;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

/**
 * 所有es公共服务
 * author:xiaojuzi
 */
public interface ISearchService {





    /**
     * 全文搜索 + 高亮显示
     * @param index 索引
     * @param clz
     * @param qo
     * @param fields
     * @param <T>
     * @return 带有分页的全文搜索(高亮显示)结果集
     */
    <T> Page<T>  searchWithHighlight(String index, Class<T> clz, SearchQueryObject qo, String... fields);



}
