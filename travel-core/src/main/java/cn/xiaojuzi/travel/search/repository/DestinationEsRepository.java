package cn.xiaojuzi.travel.search.repository;

import cn.xiaojuzi.travel.search.domain.DestinationEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * xiaojuzi
 */
public interface DestinationEsRepository extends ElasticsearchRepository<DestinationEs, String>{
}
