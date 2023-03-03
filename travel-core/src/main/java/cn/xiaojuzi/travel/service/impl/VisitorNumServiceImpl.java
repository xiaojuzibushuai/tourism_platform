package cn.xiaojuzi.travel.service.impl;

import cn.xiaojuzi.travel.domain.Visitor;
import cn.xiaojuzi.travel.domain.VisitorNum;
import cn.xiaojuzi.travel.mapper.VisitorNumMapper;
import cn.xiaojuzi.travel.redis.util.RedisKeys;
import cn.xiaojuzi.travel.service.VisitorNumService;
import cn.xiaojuzi.travel.service.VisitorService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: tourism_platform
 * @description:
 * @author: xiaojuzi
 * @create: 2022-03-08 10:27
 **/
@Service
@Transactional
public class VisitorNumServiceImpl extends ServiceImpl<VisitorNumMapper, VisitorNum> implements VisitorNumService {


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    VisitorNumService visitorNumService;

    @Autowired
    VisitorService visitorService;

    @Override
    public void addVistorNum(Long ownerId, Long userinfoId) {
        // 如果访问自己的空间，啥也不干
        if (ownerId.equals(userinfoId)){
            return;
        }else {
            if (visitorService.getOne(new QueryWrapper<Visitor>().eq("visitor_id",userinfoId).eq("owner_id",ownerId))== null){
                // 先添加记录到访客表
                visitorService.save(new Visitor(userinfoId,ownerId));
            }
            // 先去redis中看看key是否为空
            String key = RedisKeys.VISITOR_NUM_TODAY.join(ownerId.toString());

            // 先处理今日访问
            if (stringRedisTemplate.opsForValue().get(key) == null){
                // 如果不存在这个key，说明是第一次访问
                stringRedisTemplate.opsForValue().set(key,"1");
            }else {
                String value = stringRedisTemplate.opsForValue().get(key);
                int todayNum = Integer.parseInt(value);
                stringRedisTemplate.opsForValue().set(key,Integer.toString(todayNum+1));
            }
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("owner_id",ownerId);
            VisitorNum visitorNum = visitorNumService.getOne(queryWrapper);
            if (visitorNum == null){
                // 说明是第一次访问，先将数据库中的数据置为1
                visitorNum = new VisitorNum().setOwnerId(ownerId).setTotalVisitorNumber(1L);
                visitorNumService.save(visitorNum);
            }else {
                // 说明不是第一次访问
                visitorNum.setTotalVisitorNumber(visitorNum.getTotalVisitorNumber()+1);
                visitorNumService.updateById(visitorNum);
            }
        }
    }

    @Override
    public VisitorNum queryVisitorNum(Long ownerId) {
        String key = RedisKeys.VISITOR_NUM_TODAY.join(ownerId.toString());
        Long todayNum ;
        String tooday = stringRedisTemplate.opsForValue().get(key);
        if (tooday != null){
            todayNum = Long.parseLong(tooday);
        }else {
            todayNum = 0L;
        }
        VisitorNum visitorNum = visitorNumService
                .getOne(new QueryWrapper<VisitorNum>().eq("owner_id", ownerId));
        if (visitorNum != null){
            visitorNum.setTodayVisitorNumber(todayNum);
        }else {
            visitorNum = new VisitorNum(ownerId, 0L, 0L);
        }
        return visitorNum;
    }
    }
