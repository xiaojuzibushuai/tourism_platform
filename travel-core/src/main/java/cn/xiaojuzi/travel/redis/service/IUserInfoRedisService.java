package cn.xiaojuzi.travel.redis.service;


import cn.xiaojuzi.travel.domain.UserInfo;

/**
 * 用户缓存服务
 * author:xiaojuzi
 */
public interface IUserInfoRedisService {

    /**
     * 将验证码设置到redis中
     * @param phone
     * @param code
     */
    void setVerifyCode(String phone, String code);

    /**
     * 获取短信验证码
     * @param phone
     * @return
     */
    String getVerifyCode(String phone);

    /**
     * 创建token并缓存登录对象
     * @param user
     * @return
     */
    String setToken(UserInfo user);

    /**
     * 通过token换取user对象
     * @param token
     * @return
     */
    UserInfo getUserByToken(String token);
}
