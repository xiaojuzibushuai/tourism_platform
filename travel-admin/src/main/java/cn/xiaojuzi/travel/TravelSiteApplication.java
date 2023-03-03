package cn.xiaojuzi.travel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @program: tourism_platform
 * @description:
 * @author: xiaojuzi
 * @create: 2022-02-07 15:16
 **/

@SpringBootApplication
@EnableScheduling //定时任务
public class TravelSiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(TravelSiteApplication.class,args);
    }
}
