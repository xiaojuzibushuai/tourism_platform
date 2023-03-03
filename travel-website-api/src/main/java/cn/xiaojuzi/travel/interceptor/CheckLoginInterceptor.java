package cn.xiaojuzi.travel.interceptor;

import cn.xiaojuzi.travel.annotation.RequireLogin;
import cn.xiaojuzi.travel.domain.UserInfo;
import cn.xiaojuzi.travel.redis.service.IUserInfoRedisService;
import cn.xiaojuzi.travel.util.JsonResult;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * author:xiaojuzi
 */
public class CheckLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserInfoRedisService userInfoRedisService;
    
    //登录拦截
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        /**
         * HandlerMethod : 请求映射方法信息(方法所在类，方法信息， 映射路劲， 方法上注解， 方法参数......)封装对象
         *
         *
         * springmvc 拦截器操作过程解释：
         *
         * 1：spring web容器启动之后， 会查找所有controller类中所有贴了：@ReqeustMapping注解请求映射方法，并解析
         *    将解析出来的请求映射方法所有信息封装成独立对象： HandlerMethod
         * 2：spring 为了方便管理这些请求映射方法信息封装对象，定义一个类似于Map集合来管理这些对象：
         *      key：请求映射方法路径     value：请求映射方法信息封装对象（HandlerMethod实例）
         *
         *      map：{
         *
         *          "/users/currentUser" : /users/currentUser映射方法对应的HandlerMethod实例对象
         *          "/users/get"         : /users/get映射方法对应的HandlerMethod实例对象
         *          ....
         *      }
         *
         * 3：当请求进入前端控制器，springmvc会解析请求， 获取到请求路径：/user/currentUser，
         *    然后通过路径获取当前请求映射方法信息封装对象
         *        HandlerMethod handler = map.get("/user/currentUser");
         *
         * 4：调用拦截器（CheckLoginInterceptor）的   preHandle执行拦截逻辑
         *    @Autowired
         *    CheckLoginInterceptor  intercept；
         *    intercept.preHandle(request,response, handler);
         *
         *
         */

        //解决跨域问题
        //springmvc 拦截资源类型3种：
        //1>静态资源， handler对象就不是HandlerMethod实例
        //2>跨域预请求，handler对象也不是HandlerMethod实例
        //3>动态请求(映射方法)，handler就是是HandlerMethod实例
        if( !(handler instanceof HandlerMethod)){
            return true;
        }
        //1：判断当前请求对应请求映射方法是否贴@RequireLogin标签
        HandlerMethod hm = (HandlerMethod) handler;
        //2:如果有， 进行登录校验
        String token = request.getHeader("token");
        UserInfo user = userInfoRedisService.getUserByToken(token);
        if(hm.hasMethodAnnotation(RequireLogin.class)){
            //2:如果有， 进行登录校验
            //String token = request.getHeader("token");
            //UserInfo user = userInfoRedisService.getUserByToken(token);
            if(user == null){
                //返回json格式：没有登录提示
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(JSON.toJSONString(JsonResult.noLogin()));
                return false;
            }
        }
        //3：如果没有放行
        //放行
        return true;
    }
}
