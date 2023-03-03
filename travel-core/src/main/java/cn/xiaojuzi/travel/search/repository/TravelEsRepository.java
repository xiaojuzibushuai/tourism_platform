package cn.xiaojuzi.travel.search.repository;

import cn.xiaojuzi.travel.search.domain.TravelEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * xiaojuzi
 */
public interface TravelEsRepository extends ElasticsearchRepository<TravelEs, String>{
}
