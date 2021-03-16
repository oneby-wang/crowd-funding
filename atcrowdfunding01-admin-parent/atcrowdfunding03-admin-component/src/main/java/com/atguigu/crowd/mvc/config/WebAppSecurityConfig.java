package com.atguigu.crowd.mvc.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.atguigu.crowd.constant.CrowdConstant;

// 表示当前类是一个配置类
@Configuration

// 启用Web环境下权限控制功能
@EnableWebSecurity

//启用全局方法权限控制功能，并且设置prePostEnabled = true。保证@PreAuthority、@PostAuthority、@PreFilter、@PostFilter生效
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity security) throws Exception {
		
		security
			.authorizeRequests()						// 对请求进行授权
			.antMatchers("/admin/to/login/page.html")	// 针对登录页进行设置
			.permitAll()								// 无条件访问
			
			// 放行静态资源
			.antMatchers("/bootstrap/**")	
			.permitAll()                    
			.antMatchers("/crowd/**")       
			.permitAll()                    
			.antMatchers("/css/**")         
			.permitAll()                    
			.antMatchers("/fonts/**")       
			.permitAll()                    
			.antMatchers("/img/**")         
			.permitAll()                    
			.antMatchers("/jquery/**")      
			.permitAll()                    
			.antMatchers("/layer/**")       
			.permitAll()                    
			.antMatchers("/script/**")      
			.permitAll()                    
			.antMatchers("/ztree/**")       
			.permitAll()
			.antMatchers("/admin/get/page.html")		// 针对管理员分页页面设定访问控制
			// .hasRole("经理")							// 要求具备经理角色
			.access("hasRole('经理') OR hasAuthority('user:get')")		// 要求具备“经理”角色和“user:get”权限二者之一
			.anyRequest()					// 其他任意请求
			.authenticated()				// 认证后访问
			.and()
			.exceptionHandling()
			.accessDeniedHandler(new AccessDeniedHandler() {		
				@Override
				public void handle(HttpServletRequest request, HttpServletResponse response,
						AccessDeniedException accessDeniedException) throws IOException, ServletException {
					request.setAttribute("exception", new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
					request.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(request, response);
				}
			})
			.and()
			.csrf()							// 防跨站请求伪造功能
			.disable()						// 禁用
			.formLogin()					// 开启表单登录的功能
			.loginPage("/admin/to/login/page.html")			// 指定登录页面
			.loginProcessingUrl("/security/do/login.html")	// 指定处理登录请求的地址
			.defaultSuccessUrl("/admin/to/main/page.html")	// 指定登录成功后前往的地址
			.usernameParameter("loginAcct")	// 账号的请求参数名称
			.passwordParameter("userPswd")	// 密码的请求参数名称
			.and()
			.logout()						// 开启退出登录功能
			.logoutUrl("/seucrity/do/logout.html")			// 指定退出登录地址
			.logoutSuccessUrl("/admin/to/login/page.html")	// 指定退出成功以后前往的地址
			;
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		
		// 临时使用内存版登录的模式测试代码
		/*
		builder
			.inMemoryAuthentication()
			.withUser("Heygo")
			.password("123123")
			.roles("ADMIN");
		*/
		
		builder
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder);
		
	}
	
}
