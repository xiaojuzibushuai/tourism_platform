package cn.xiaojuzi.travel.service;

import cn.xiaojuzi.travel.domain.VisitorNum;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @program: tourism_platform
 * @description:
 * @author: xiaojuzi
 * @create: 2022-03-08 10:26
 **/
public interface VisitorNumService extends IService<VisitorNum> {

    void addVistorNum(Long ownerId, Long userinfoId);

    VisitorNum queryVisitorNum(Long ownerId);
}
