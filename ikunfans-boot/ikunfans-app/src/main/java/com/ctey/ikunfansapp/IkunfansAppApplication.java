package com.ctey.ikunfansapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@MapperScan("com.ctey.ikunfansdb.Mapper")
@ComponentScan(basePackages = {
        "com.ctey.ikunfanscommon",
        "com.ctey.ikunfansapp",
        "com.ctey.ikunfansdb",
        "com.ctey.ikunfansweb",
        "com.ctey.ikunfansconnect"
})
public class IkunfansAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(IkunfansAppApplication.class, args);
    }
}
