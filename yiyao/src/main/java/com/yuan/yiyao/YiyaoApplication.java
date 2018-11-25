package com.yuan.yiyao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement//开启事务管理
public class YiyaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(YiyaoApplication.class, args);
    }
}
