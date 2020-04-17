package com.tensquare.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @program: tensquare
 * @description:
 **/
@SpringBootApplication
public class BaseApplication {

    public static void main(String[] args) {

        SpringApplication.run(BaseApplication.class);
    }

    //配置Idwork对象 生成主键值
    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
