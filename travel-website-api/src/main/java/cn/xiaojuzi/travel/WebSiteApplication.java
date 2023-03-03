package cn.xiaojuzi.travel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: tourism_platform
 * @description: 前台页面接口启动类
 * @author: xiaojuzi
 * @create: 2022-02-06 20:17
 **/

@SpringBootApplication
@EnableSwagger2//开启Swagger2
@EnableScheduling
public class WebSiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebSiteApplication.class,args);
    }
}
