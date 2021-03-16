package com.atguigu.crowd.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrowdTest {

	private Logger logger = LoggerFactory.getLogger(CrowdTest.class);

	@Test
	public void testSendMessage() {

		// 短信调用接接口的URL地址
		String host = "https://fesms.market.alicloudapi.com";

		// 具体发送短信功能的地址
		String path = "/sms/";

		// 请求方式
		String method = "GET";

		// 登录到阿里云，进入控制台，找到自己的短信接口AppCode
		String appcode = "9844f3f479cf41ea92ccbea03c70db58";

		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + appcode);

		// 封装其他参数
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("code", "123456"); 		// 要发送的验证码
		querys.put("phone", "13262792031"); // 收短信人的手机号
		querys.put("sign", "1"); 			// 签名编号
		querys.put("skin", "1"); 			// 模板编号

		// JDK 1.8示例代码请在这里下载： http://code.fegine.com/Tools.zip

		try {
			/**
			 * 重要提示如下: HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 或者直接下载： http://code.fegine.com/HttpUtils.zip 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 * 相关jar包（非pom）直接下载： http://code.fegine.com/aliyun-jar.zip
			 */
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);

			StatusLine statusLine = response.getStatusLine();

			// 状态码: 200 正常；400 URL无效；401 appCode错误； 403 次数用完； 500 API网管错误
			int statusCode = statusLine.getStatusCode();
			logger.info("code=" + statusCode);

			String reasonPhrase = statusLine.getReasonPhrase();
			logger.info("reason=" + reasonPhrase);

			// System.out.println(response.toString());如不输出json, 请打开这行代码，打印调试头部状态码。
			// 获取response的body
			logger.info(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSendMessageUtil() {

		// 短信调用接接口的URL地址
		String host = "https://fesms.market.alicloudapi.com";

		// 具体发送短信功能的地址
		String path = "/sms/";

		// 请求方式
		String method = "GET";

		// 收件人电话号码
		String phoneNum = "13262792031";

		// 登录到阿里云，进入控制台，找到自己的短信接口AppCode
		String appCode = "9844f3f479cf41ea92ccbea03c70db58";

		// 签名编号
		String sign = "1";

		// 模板编号
		String skin = "1";

		// 发送短信，获取响应信息
		ResultEntity<String> resultEntity = CrowdUtil.sendCodeByShortMessage(host, path, method, phoneNum, appCode, sign, skin);

		logger.info(resultEntity.toString());
	}

}
