package cn.xiaojuzi.travel.service.impl;

import cn.xiaojuzi.travel.domain.*;
import cn.xiaojuzi.travel.mapper.StrategyContentMapper;
import cn.xiaojuzi.travel.mapper.StrategyMapper;
import cn.xiaojuzi.travel.query.StrategyQuery;
import cn.xiaojuzi.travel.redis.vo.StrategyStatisVO;
import cn.xiaojuzi.travel.service.IDestinationService;
import cn.xiaojuzi.travel.service.IStrategyCatalogService;
import cn.xiaojuzi.travel.service.IStrategyService;
import cn.xiaojuzi.travel.service.IStrategyThemeService;
import cn.xiaojuzi.travel.util.JsonResult;
import cn.xiaojuzi.travel.vo.DestVO;
import cn.xiaojuzi.travel.vo.ThemeVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* 攻略明细服务接口实现
 * author:xiaojuzi
*/
@Service
@Transactional
public class StrategyServiceImpl extends ServiceImpl<StrategyMapper,Strategy> implements IStrategyService {

    @Autowired
    private IStrategyCatalogService strategyCatalogService;

    @Autowired
    private IStrategyThemeService strategyThemeService;

    @Autowired
    private IDestinationService destinationService;


    @Autowired
    private StrategyContentMapper strategyContentMapper;


    @Override
    public IPage<Strategy> queryPage(StrategyQuery qo) {
        IPage<Strategy> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<Strategy> wrapper = Wrappers.<Strategy>query();

        wrapper.eq(qo.getDestId() != null, "dest_id", qo.getDestId())
                .eq(qo.getThemeId() != null, "theme_id", qo.getThemeId())
                .orderByDesc(qo.getOrderBy());

        if(qo.getType() != null){
            if(qo.getType() == StrategyCondition.TYPE_THEME) {
                wrapper.eq("theme_id", qo.getRefid());
            }else if(qo.getType() == StrategyCondition.TYPE_ABROAD || qo.getType() == StrategyCondition.TYPE_CHINA){
                wrapper.eq("dest_id", qo.getRefid());
            }
        }

        return super.page(page, wrapper);
    }


    @Override
    public boolean saveOrUpdate(Strategy entity) {

        StrategyCatalog catalog = strategyCatalogService.getById(entity.getCatalogId());

        //目的地id与名称
        entity.setDestId(catalog.getDestId());
        entity.setDestName(catalog.getDestName());
        //分类name
        entity.setCatalogName(catalog.getName());

        //主题name
        StrategyTheme theme = strategyThemeService.getById(entity.getThemeId());
        entity.setThemeName(theme.getName());

        //是否国外
        List<Destination> toasts = destinationService.queryToasts(catalog.getDestId());
        if(toasts != null && toasts.size() > 0){
            if("中国".equals(toasts.get(0).getName())){
                //国内
                entity.setIsabroad(Strategy.ABROAD_NO);
            }else{
                entity.setIsabroad(Strategy.ABROAD_YES);
            }
        }
        boolean flag = false;
        StrategyContent content = entity.getContent();
        //攻略内容
        if (entity.getId() != null){
            //攻略：
            flag = super.updateById(entity);
            //内容
            content.setId(entity.getId());
            strategyContentMapper.updateById(content);

        }else {
            //创建时间
            entity.setCreateTime(new Date());
            //各种统计数默认为0
            entity.setViewNum(0);
            entity.setFavorNum(0);
            entity.setShareNum(0);
            entity.setThumbsupNum(0);
            entity.setReplyNum(0);

            flag = this.save(entity);

            content.setId(entity.getId());
            strategyContentMapper.insert(content);
        }

        //同步修改es中数据
        //假设操作失败抛出runtimeex
        //触发自定义事件， 让监控程序执行

        return flag;
    }

    @Override
    public StrategyContent getContent(Long id) {
        return strategyContentMapper.selectById(id);
    }

    @Override
    public List<Strategy> queryViewnumTop3(Long destId) {

        QueryWrapper<Strategy> wrapper = new QueryWrapper<>();
        //1:指定目的地
        wrapper.eq("dest_id", destId)
        //2:点击量排序
        .orderByDesc("viewnum")
        //3:前3篇
        .last("limit 3");
        return super.list(wrapper);
    }

    @Override
    public void updateStatisVO(StrategyStatisVO vo) {

        UpdateWrapper<Strategy> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", vo.getStrategyId())
                .set("viewnum", vo.getViewnum())
                .set("replynum", vo.getReplynum())
                .set("favornum", vo.getFavornum())
                .set("sharenum", vo.getSharenum())
                .set("thumbsupnum", vo.getThumbsupnum());
        super.update(wrapper);
    }

    @Override
    public List<Strategy> queryByDestId(Long destId) {
        return super.list(Wrappers.<Strategy>query().eq("dest_id",destId));
    }


    @Override
    public List<ThemeVO> queryThemeCommand() {
        List<ThemeVO> list = new ArrayList<>();
        QueryWrapper<Strategy> wrapper = Wrappers.<Strategy>query()
                .select("theme_name, count(id) count, GROUP_CONCAT(dest_id) ids,  GROUP_CONCAT(dest_name) names")
                .groupBy("theme_name")
                .orderByDesc("count");
        List<Map<String, Object>> mapList = super.listMaps(wrapper);


        List<String> ds = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            ThemeVO vo = new ThemeVO();
            vo.setThemeName(map.get("theme_name").toString());
            List<DestVO> dests = new ArrayList<>();

            String[] ids = map.get("ids").toString().split(",");

            String[] names = map.get("names").toString().split(",");
            for(int i = 0; i < names.length; i++){
                if(ds.contains(names[i])){
                    continue;
                }else{
                    DestVO dest = new DestVO(Long.valueOf(ids[i]), names[i]);
                    dests.add(dest);
                    ds.add(names[i]);
                }
            }
            ds.clear();
            vo.setDests(dests);
            list.add(vo);
        }
        return list;
    }

    @Override
    public JsonResult updateState(Integer state, Long id) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("state", state);
        super.update(updateWrapper);
        return JsonResult.success();
    }

}
