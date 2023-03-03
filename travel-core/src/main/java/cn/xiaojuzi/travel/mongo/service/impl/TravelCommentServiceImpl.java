package cn.xiaojuzi.travel.mongo.service.impl;

import cn.xiaojuzi.travel.mongo.domain.TravelComment;
import cn.xiaojuzi.travel.mongo.query.TravelCommentQuery;
import cn.xiaojuzi.travel.mongo.repository.TravelCommentRepository;
import cn.xiaojuzi.travel.mongo.service.ITravelCommentService;
import cn.xiaojuzi.travel.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * author:xiaojuzi
 */
@Service
public class TravelCommentServiceImpl implements ITravelCommentService {
    @Autowired
    private TravelCommentRepository repository;

    @Autowired
    private MongoTemplate template;

    @Override
    public void commentAdd(TravelComment comment) {
               comment.setId(null);
        comment.setCreateTime(new Date());

        //维护评论的评论（维护引用评论）
        String refId = comment.getRefComment().getId();
        if (StringUtils.hasLength(refId)) {
            //评论的评论
            Optional<TravelComment> op = repository.findById(refId);
            if(op.isPresent()){
                comment.setRefComment(op.get());
            }
            comment.setType(TravelComment.TRAVLE_COMMENT_TYPE);
        }else{
            //普通评论
            comment.setType(TravelComment.TRAVLE_COMMENT_TYPE_COMMENT);
        }

        repository.save(comment);
    }

    @Override
    public Page<TravelComment> queryPage(TravelCommentQuery qo) {

        //回想当初PageResult
        //页面传入： currentPage  pageSize
        //查询出来： totalCount   data
        //计算出来： totalPage    next   prev
        Query query = new Query();
        if(qo.getTravelId() != null){
            query.addCriteria(Criteria.where("travelId").is(qo.getTravelId()));
        }
        long totalCount = template.count(query, TravelComment.class);
        if(totalCount == 0){
            return Page.empty();
        }
        //分页 limit ？， ？
        //query.skip((qo.getCurrentPage() -1) * qo.getPageSize()).limit(qo.getPageSize());

        //分页参数对象： 参数1：当前页，注意：从0开始算起  参数2：每页显示条数
        Pageable pageable = PageRequest.of(qo.getCurrentPage() - 1, qo.getPageSize());
        query.with(pageable);

        //select * from xxx where ....  limit
        List<TravelComment> data = template.find(query, TravelComment.class);

        return new PageImpl(data, pageable,totalCount);
    }

    @Override
    public List<TravelComment> queryByTravelId(Long travelId) {
        return repository.findByTravelId(travelId);
    }
}
