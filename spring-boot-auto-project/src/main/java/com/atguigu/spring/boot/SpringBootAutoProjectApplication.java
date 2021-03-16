package com.atguigu.spring.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.atguigu.spring.boot.mapper")
@SpringBootApplication
public class SpringBootAutoProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAutoProjectApplication.class, args);
	}

}
