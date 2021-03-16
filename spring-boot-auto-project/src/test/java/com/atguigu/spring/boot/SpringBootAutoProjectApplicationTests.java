package com.atguigu.spring.boot;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.atguigu.spring.boot.entity.Student;

@SpringBootTest
class SpringBootAutoProjectApplicationTests {

	@Autowired
	private Student student;
	
	Logger logger = LoggerFactory.getLogger(SpringBootAutoProjectApplicationTests.class);
	
	@Value("${atguigu.best.wishes}")
	private String wishes;
	
	@Test
	void contextLoads() {
	
		logger.info(student.toString());
		logger.info(wishes);
		
	}

}
