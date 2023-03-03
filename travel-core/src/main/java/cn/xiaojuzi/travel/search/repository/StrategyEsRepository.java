package cn.xiaojuzi.travel.search.repository;

import cn.xiaojuzi.travel.search.domain.StrategyEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * xiaojuzi
 */
public interface StrategyEsRepository extends ElasticsearchRepository<StrategyEs, String>{
}
