package com.ljx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.ljx.dao")
@SpringBootApplication
public class MybatisplusQuickstartApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisplusQuickstartApplication.class, args);
    }

}
