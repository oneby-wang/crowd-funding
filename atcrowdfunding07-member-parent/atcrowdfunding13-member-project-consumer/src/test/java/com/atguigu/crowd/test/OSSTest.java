package com.atguigu.crowd.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.atguigu.crowd.config.OSSProperties;
import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OSSTest {

	@Autowired
	private OSSProperties oSSProperties;

	@Test
	public void testOSS() throws FileNotFoundException {

		FileInputStream inputStream = new FileInputStream("SpongeBob.jpg");

		ResultEntity<String> resultEntity = CrowdUtil.uploadFileToOss(
				oSSProperties.getEndPoint(),
				oSSProperties.getAccessKeyId(), 
				oSSProperties.getAccessKeySecret(), 
				inputStream,
				oSSProperties.getBucketName(), 
				oSSProperties.getBucketDomain(), 
				"SpongeBob.jpg");

		System.out.println(resultEntity.getResult());
		
	}
	
}
