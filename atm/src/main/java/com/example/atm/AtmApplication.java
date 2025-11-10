package com.example.atm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.example.atm.mapper")
@SpringBootApplication
public class AtmApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtmApplication.class, args);
    }

}
