package cn.xiaojuzi.travel.resolver;

import cn.xiaojuzi.travel.annotation.UserParam;
import cn.xiaojuzi.travel.domain.UserInfo;
import cn.xiaojuzi.travel.redis.service.IUserInfoRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;


/**
 * 自定义参数解析器
 * 作用：
 *    将接口中声明UserInfo类型的参数，解析成当前登录用户对象， 并注入
 *    author:xiaojuzi
 */
public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private IUserInfoRedisService userInfoRedisService;

    //用于识别接口参数类型
    //指定当前解析器能解析接口参数的类型： 此处是UserInfo.class类型
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType() == UserInfo.class
                && methodParameter.hasParameterAnnotation(UserParam.class)
                ;
    }


    //用于解析接口参数，并注入参数值
    //前提： supportsParameter 方法返回true之后执行
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String token = request.getHeader("token");
        UserInfo userInfo = userInfoRedisService.getUserByToken(token);
        return userInfo;
    }
}
