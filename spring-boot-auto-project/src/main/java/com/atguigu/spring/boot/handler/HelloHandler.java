package com.atguigu.spring.boot.handler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloHandler {
	
	@RequestMapping("/hello")
	public String hello() {
		return "Hello from Heygo~";
	}
	
	@RequestMapping("/hello/thymeleaf")
	public String helloTthymeleaf() {
		return "hello-thymeleaf";
	}

}
