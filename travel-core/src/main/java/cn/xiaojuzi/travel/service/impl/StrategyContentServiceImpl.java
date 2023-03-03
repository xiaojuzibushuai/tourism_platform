package cn.xiaojuzi.travel.service.impl;

import cn.xiaojuzi.travel.domain.StrategyContent;
import cn.xiaojuzi.travel.mapper.StrategyContentMapper;
import cn.xiaojuzi.travel.service.StrategyContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: tourism_platform
 * @description:
 * @author: xiaojuzi
 * @create: 2022-03-08 10:15
 **/
@Service
@Transactional
public class StrategyContentServiceImpl extends ServiceImpl<StrategyContentMapper, StrategyContent> implements
        StrategyContentService {
}
