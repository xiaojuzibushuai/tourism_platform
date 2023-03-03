package cn.xiaojuzi.travel.service;


import cn.xiaojuzi.travel.domain.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户服务层接口
 * author:xiaojuzi
 */
public interface IUserInfoService extends IService<UserInfo> {
    /**
     * 判断指定手机号码是否存在
     * @param phone
     * @return true：手机号码存在 false：手机号码不存在
     */
    boolean checkPhone(String phone);

    /**
     * 短信发送
     * @param phone
     */
    void sendVerifyCode(String phone);

    /**
     * 用户注册
     * @param phone
     * @param nickname
     * @param password
     * @param rpassword
     * @param verifyCode
     */
    void regist(String phone, String nickname, String password, String rpassword, String verifyCode);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    UserInfo login(String username, String password);

    /**
     * 查询指定城市下用户
     * @param city
     * @return
     */
    List<UserInfo> queryByCity(String city);

    /**
     * 校验根据id查询的用户是否存在
     * @param id
     * @return
     */
    UserInfo checkUserById(long id);

    boolean favor(Long sid, Long userId);

}
