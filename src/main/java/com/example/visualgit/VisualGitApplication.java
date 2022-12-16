package com.example.visualgit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.visualgit.mapper")
public class VisualGitApplication {
    public static void main(String[] args) {
        SpringApplication.run(VisualGitApplication.class, args);
    }
}
