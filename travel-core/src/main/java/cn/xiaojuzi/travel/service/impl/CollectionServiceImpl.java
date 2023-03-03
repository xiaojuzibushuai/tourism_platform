package cn.xiaojuzi.travel.service.impl;

import cn.xiaojuzi.travel.domain.Collection;
import cn.xiaojuzi.travel.mapper.CollectionMapper;
import cn.xiaojuzi.travel.service.CollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: tourism_platform
 * @description:
 * @author: xiaojuzi
 * @create: 2022-03-08 10:06
 **/
@Service
@Transactional
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements
        CollectionService {
}
