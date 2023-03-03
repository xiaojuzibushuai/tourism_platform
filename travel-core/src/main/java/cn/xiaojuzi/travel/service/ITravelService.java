package cn.xiaojuzi.travel.service;

import cn.xiaojuzi.travel.domain.Travel;
import cn.xiaojuzi.travel.domain.TravelContent;
import cn.xiaojuzi.travel.query.TravelQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 游记服务接口
 * author:xiaojuzi
 */
public interface ITravelService extends IService<Travel>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<Travel> queryPage(TravelQuery qo);

    /**
     * 查内容
     * @param id
     * @return
     */
    TravelContent getContent(Long id);

    /**
     * 审核
     * @param id
     * @param state
     */
    void audit(Long id, int state);

    /**
     * 指定目的地下点击量前3
     * @param destId
     * @return
     */
    List<Travel> queryViewnumTop3(Long destId);

    /**
     * 查询目的地下游记
     * @param destId
     * @return
     */
    List<Travel> queryByDestId(Long destId);
}
