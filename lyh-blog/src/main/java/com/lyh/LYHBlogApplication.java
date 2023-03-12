package com.lyh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Author:crushlyh
 * Date:2023/2/18 19:46
 */
@EnableSwagger2
@SpringBootApplication
@MapperScan("com.lyh.mapper")
@EnableScheduling
public class LYHBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(LYHBlogApplication.class,args);
    }
}
