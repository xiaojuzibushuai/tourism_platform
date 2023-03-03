package cn.xiaojuzi.travel.service;

import cn.xiaojuzi.travel.domain.Visitor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @program: tourism_platform
 * @description:
 * @author: xiaojuzi
 * @create: 2022-03-08 10:30
 **/
public interface VisitorService extends IService<Visitor> {
    List<Visitor> queryVisitor(Long ownerId, Long userId);
}
