package cn.xiaojuzi.travel.search.repository;

import cn.xiaojuzi.travel.search.domain.UserInfoEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * xiaojuzi
 */
public interface UserInfoEsRepository extends ElasticsearchRepository<UserInfoEs, String>{
}
