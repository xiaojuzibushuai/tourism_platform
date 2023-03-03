package cn.xiaojuzi.travel.redis.service;

/**
 * 接口防护服务层
 * author:xiaojuzi
 */
public interface ISecurityRedisService {
    /**
     * 是否放行
     * @param key
     * @return true: 放， false： 不放
     */
    boolean isAllowBrush(String key);
}
