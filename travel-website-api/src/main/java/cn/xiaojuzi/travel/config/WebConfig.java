package cn.xiaojuzi.travel.config;

import cn.xiaojuzi.travel.interceptor.BrushProofInterceptor;
import cn.xiaojuzi.travel.interceptor.CheckLoginInterceptor;
import cn.xiaojuzi.travel.interceptor.SignInterceptor;
import cn.xiaojuzi.travel.resolver.UserInfoArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer{


    //自定义用户参数解析器
    @Bean
    public UserInfoArgumentResolver userInfoArgumentResolver(){
        return new UserInfoArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userInfoArgumentResolver());

    }

    //配置拦截器
    @Bean
    public CheckLoginInterceptor checkLoginInterceptor(){
        return new CheckLoginInterceptor();
    }

    @Bean
    public BrushProofInterceptor brushProofInterceptor(){
        return  new BrushProofInterceptor();
    }

    @Bean
    public SignInterceptor signInterceptor(){
        return  new SignInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/users/checkPhone")
                .excludePathPatterns("/users/sendVerifyCode")
                .excludePathPatterns("/users/regist")
                .excludePathPatterns("/users/login");
        //防刷
        //registry.addInterceptor(brushProofInterceptor())
        //        .addPathPatterns("/**");
        //签名
        //registry.addInterceptor(signInterceptor())
        //        .addPathPatterns("/**");


    }

    //跨域访问
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            //重写父类提供的跨域请求处理的接口
            public void addCorsMappings(CorsRegistry registry) {
                //添加映射路径
                registry.addMapping("/**")
                        //放行哪些原始域
                        .allowedOriginPatterns("*")
                        //是否发送Cookie信息
                        .allowCredentials(true)
                        //放行哪些原始域(请求方式)
                        .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
                        //放行哪些原始域(头部信息)
                        .allowedHeaders("*")
                        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
                        .exposedHeaders("Header1", "Header2");
            }
        };
    }
}
