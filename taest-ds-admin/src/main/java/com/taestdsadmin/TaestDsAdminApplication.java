package com.taestdsadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.taestdsadmin.dao")
public class TaestDsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaestDsAdminApplication.class, args);
    }

}
