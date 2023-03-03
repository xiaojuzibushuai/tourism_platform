package cn.xiaojuzi.travel.service.impl;

import cn.xiaojuzi.travel.domain.Visitor;
import cn.xiaojuzi.travel.mapper.VisitorMapper;
import cn.xiaojuzi.travel.service.IUserInfoService;
import cn.xiaojuzi.travel.service.VisitorService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: tourism_platform
 * @description:
 * @author: xiaojuzi
 * @create: 2022-03-08 10:30
 **/
@Service
@Transactional
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements VisitorService {

    @Autowired
    VisitorService visitorService;

    @Autowired
    IUserInfoService userInfoService;

    @Override
    public List<Visitor> queryVisitor(Long ownerId, Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("owner_id", ownerId);
        List<Visitor> list = visitorService.list(queryWrapper);
        for (Visitor visitor : list) {
            visitor.setVisitor(userInfoService.getById(visitor.getVisitorId()));
        }
        return list;
    }
}
