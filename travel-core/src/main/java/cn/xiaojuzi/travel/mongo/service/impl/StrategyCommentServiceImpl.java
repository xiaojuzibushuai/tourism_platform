package cn.xiaojuzi.travel.mongo.service.impl;

import cn.xiaojuzi.travel.mongo.domain.StrategyComment;
import cn.xiaojuzi.travel.mongo.query.StrategyCommentQuery;
import cn.xiaojuzi.travel.mongo.repository.StrategyCommentRepository;
import cn.xiaojuzi.travel.mongo.service.IStrategyCommentService;
import cn.xiaojuzi.travel.redis.service.IStrategyStatisVORedisService;
import cn.xiaojuzi.travel.redis.vo.StrategyStatisVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * author:xiaojuzi
 */
@Service
public class StrategyCommentServiceImpl implements IStrategyCommentService {
    @Autowired
    private StrategyCommentRepository strategyCommentRepository;

    @Autowired
    IStrategyStatisVORedisService strategyStatisticsRedisService;

    @Autowired
    private MongoTemplate template;

    @Override
    public void commentAdd(StrategyComment strategyComment) {
        strategyComment.setId(null);
        strategyComment.setCreateTime(new Date());
        strategyCommentRepository.save(strategyComment);
        StrategyStatisVO vo = strategyStatisticsRedisService
                .getStrategyVO(strategyComment.getStrategyId());
        vo.setReplynum(vo.getReplynum()+1);
        strategyStatisticsRedisService.setStrategyVO(vo);
    }

    @Override
    public Page<StrategyComment> queryForList(StrategyCommentQuery qo) {

        //回想当初PageResult
        //页面传入： currentPage  pageSize
        //查询出来： totalCount   data
        //计算出来： totalPage    next   prev
        Query query = new Query();
        if(qo.getStrategyId() != null){
            query.addCriteria(Criteria.where("strategyId").is(qo.getStrategyId()));
        }
        long totalCount = template.count(query, StrategyComment.class);
        if(totalCount == 0){
            return Page.empty();
        }

        //分页参数对象： 参数1：当前页，注意：从0开始算起  参数2：每页显示条数
        Pageable pageable = PageRequest.of(qo.getCurrentPage() - 1, qo.getPageSize());
        query.with(pageable);

        List<StrategyComment> data = template.find(query, StrategyComment.class);
        return new PageImpl(data, pageable,totalCount);
    }

    public StrategyComment get(String id) {
        return strategyCommentRepository.findById(id).orElse(null);
    }
    public void update(StrategyComment strategyComment) {
        strategyCommentRepository.save(strategyComment);
    }

    @Override
    public void commentThumb(String cid, Long uid) {

        //1:获取用户点赞id集合
        StrategyComment comment = this.get(cid);
        List<Long> userIds = comment.getThumbuplist();

        //2:判断uid是否在集合中
        if(userIds.contains(uid)){
            //3:如果在, 表示取消点赞, 点赞数-1, 移除uid
            comment.setThumbupnum(comment.getThumbupnum() - 1);
            userIds.remove(uid);
        }else{
            //4:如果不在, 表示点赞, 点赞数+1, 加uid
            comment.setThumbupnum(comment.getThumbupnum() + 1);
            userIds.add(uid);
        }
        //comment.setThumbuplist(userIds);
        //更新
        this.update(comment);
    }
}
