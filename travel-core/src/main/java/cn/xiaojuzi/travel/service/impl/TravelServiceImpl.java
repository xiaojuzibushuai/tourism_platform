package cn.xiaojuzi.travel.service.impl;

import cn.xiaojuzi.travel.domain.Travel;
import cn.xiaojuzi.travel.domain.TravelContent;
import cn.xiaojuzi.travel.domain.UserInfo;
import cn.xiaojuzi.travel.mapper.TravelContentMapper;
import cn.xiaojuzi.travel.mapper.TravelMapper;
import cn.xiaojuzi.travel.query.TravelCondition;
import cn.xiaojuzi.travel.query.TravelQuery;
import cn.xiaojuzi.travel.service.ITravelService;
import cn.xiaojuzi.travel.service.IUserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
* 游记服务接口实现
 * author:xiaojuzi
*/
@Service
@Transactional
public class TravelServiceImpl extends ServiceImpl<TravelMapper, Travel> implements ITravelService {

    @Autowired
    private TravelContentMapper travelContentMapper;

    @Autowired
    private IUserInfoService userInfoService;

    @Override
    public IPage<Travel> queryPage(TravelQuery qo) {
        IPage<Travel> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<Travel> wrapper = Wrappers.<Travel>query();
        wrapper.eq(qo.getDestId() != null , "dest_id", qo.getDestId());


        //出现天数
        //dayType = 2----->new TravelCondition(4,7)
        TravelCondition day = TravelCondition.DAY_MAP.get(qo.getDayType());
        if(day != null){
            wrapper.ge("day", day.getMin()).le("day", day.getMax());
        }


        //人均消费
        TravelCondition avg = TravelCondition.AVG_MAP.get(qo.getConsumeType());
        if(avg != null){
            wrapper.ge("avg_consume", avg.getMin()).le("avg_consume", avg.getMax());
        }

        //出行时间
        TravelCondition time = TravelCondition.TIME_MAP.get(qo.getTravelTimeType());
        if(time != null){
            wrapper.ge("DATE_FORMAT(travel_time,'%m')", time.getMin())
                    .le("DATE_FORMAT(travel_time,'%m')", time.getMax());
        }

        //排序
        wrapper.orderByDesc(StringUtils.hasLength(qo.getOrderBy()), qo.getOrderBy());


        super.page(page, wrapper);
        for (Travel record : page.getRecords()) {
            UserInfo user = userInfoService.getById(record.getAuthorId());
            record.setAuthor(user);
        }
        return page;
    }

    @Override
    public TravelContent getContent(Long id) {
        return travelContentMapper.selectById(id);
    }

    @Override
    public void audit(Long id, int state) {
        //1:满足审核条件
        Travel travel = super.getById(id);
        if(travel == null || travel.getState() != Travel.STATE_WAITING){
            throw new RuntimeException("参数异常");
        }
        //2:审核通过之后做什么
        if(state == Travel.STATE_RELEASE){
            //1>状态改为审核通过
            travel.setState(Travel.STATE_RELEASE);
            //2>发布时间为：当前时间
            travel.setReleaseTime(new Date());
            //3>最后修改时间为：当前时间
            travel.setLastUpdateTime(new Date());
            //4>记录审核信息：  审核人， 审核时间， 审核状态， 审核备注， 审核记录id....
        }else if(state == Travel.STATE_REJECT){
            //3:审核拒绝之后做什么
            //1>状态改为审核拒绝
            travel.setState(Travel.STATE_REJECT);
            //2>发布时间为：改null
            travel.setReleaseTime(null);
            //3>最后修改时间为：当前时间
            travel.setLastUpdateTime(new Date());
            //4>记录审核信息：  审核人， 审核时间， 审核状态， 审核备注， 审核记录id....
        }else{
            throw new RuntimeException("参数异常");
        }
        super.updateById(travel);
    }

    @Override
    public List<Travel> queryViewnumTop3(Long destId) {

        QueryWrapper<Travel> wrapper = Wrappers.<Travel>query();
        wrapper.eq("dest_id", destId)
                .orderByDesc("viewnum")
                .last("limit 3");
        return super.list(wrapper);
    }

    @Override
    public List<Travel> queryByDestId(Long destId) {
        List<Travel> list = super.list(Wrappers.<Travel>query().eq("dest_id", destId));
        for (Travel travel : list) {
            travel.setAuthor(userInfoService.getById(travel.getAuthorId()));
        }
        return list;
    }
}
