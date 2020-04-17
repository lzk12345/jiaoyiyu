package com.jiaoyiyu;

import com.jiaoyiyu.back.config.WebMvcConfiguration;
import com.jiaoyiyu.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.jiaoyiyu.back.coredao")
//@org.mybatis.spring.annotation.MapperScan(basePackages = "com.jiaoyiyu.back.coredao")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @Bean
    public WebMvcConfiguration webMvcConfiguration() {
        return new WebMvcConfiguration();
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }
}
