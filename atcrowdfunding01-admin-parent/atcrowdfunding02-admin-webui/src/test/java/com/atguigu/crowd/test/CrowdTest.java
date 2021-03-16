package com.atguigu.crowd.test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.mapper.RoleMapper;
import com.atguigu.crowd.service.api.AdminService;

//指定 Spring 给 Junit 提供的运行器类
@RunWith(SpringJUnit4ClassRunner.class)
//加载 Spring 配置文件的注解
@ContextConfiguration(locations = { "classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml" })
public class CrowdTest {
	@Autowired
	private DataSource dataSource;

	@Autowired
	private AdminMapper adminMapper;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Test
	public void testRoleSave() {
		for(int i = 0; i < 235; i++) {
			roleMapper.insert(new Role(null, "role"+i));
		}
	}
	
	@Test
	public void test() {
		for(int i = 0; i < 238; i++) {
			adminMapper.insert(new Admin(null, "loginAcct"+i, "4297F44B13955235245B2497399D7A93", "userName"+i, "email"+i, null));
		}
	}

	@Test
	public void testTx() {
		Admin admin = new Admin(null, "jerry", "123456", "杰瑞", "jerry@qq.com", null);
		adminService.saveAdmin(admin);
	}

	@Test
	public void testAdminMapperAutowired() {
		Admin admin = adminMapper.selectByPrimaryKey(1);
		System.out.println(admin);
	}

	@Test
	public void testDataSource() throws SQLException {
		// 1.通过数据源对象获取数据源连接
		Connection connection = dataSource.getConnection();
		// 2.打印数据库连接
		System.out.println(connection);
	}
}