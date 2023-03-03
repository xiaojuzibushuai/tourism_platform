package cn.xiaojuzi.travel.mongo.repository;


import cn.xiaojuzi.travel.mongo.domain.StrategyComment;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 攻略评论持久层操作接口
 * author:xiaojuzi
 */
public interface StrategyCommentRepository extends MongoRepository<StrategyComment, String> {
}
