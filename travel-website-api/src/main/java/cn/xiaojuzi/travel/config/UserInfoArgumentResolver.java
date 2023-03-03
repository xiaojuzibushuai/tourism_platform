package cn.xiaojuzi.travel.config;

import cn.xiaojuzi.travel.annotation.UserParam;
import cn.xiaojuzi.travel.domain.UserInfo;
import cn.xiaojuzi.travel.redis.service.IUserInfoRedisService;
import cn.xiaojuzi.travel.redis.service.impl.UserInfoRedisServiceImpl;
import cn.xiaojuzi.travel.service.impl.UserInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: tourism_platform
 * @description:
 * @author: xiaojuzi
 * @create: 2022-03-07 17:30
 **/
public class UserInfoArgumentResolver  implements HandlerMethodArgumentResolver {

    @Autowired
    IUserInfoRedisService iUserInfoRedisService;

    // 表示解析对象的类型是什么类型
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType()== UserInfo.class && methodParameter.hasParameterAnnotation(
                UserParam.class);
    }


    // 如果上面的结果返回为true才执行这一步
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String token = nativeWebRequest.getNativeRequest(HttpServletRequest.class).getHeader("token");
        return iUserInfoRedisService.getUserByToken(token);
    }
}
