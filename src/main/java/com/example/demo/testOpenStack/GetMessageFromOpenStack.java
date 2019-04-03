package com.example.demo.testOpenStack;


import java.net.URI;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.utils.PropertyUtil;

/**
 * @author Administrator
 *
 */
@Component
public class GetMessageFromOpenStack {
	private String get_token_url=PropertyUtil.getProperty("get_token_url");
	private String json=PropertyUtil.getProperty("get_token_body");
	/**
	 * 获取token的值
	 * @return
	 * @throws Exception
	 */
	public String getMessageFromOpenStack() throws Exception{
			RestTemplate restTemplate = TestOpenStackAPI.getRestTemplate();
			URI uri=new URI(get_token_url);
			HttpHeaders headers = new HttpHeaders(); 
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8); // 请求头设置ContentType
			HttpEntity<Object> httpEntity=new HttpEntity<Object>(json, headers);
			ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, httpEntity, String.class);
			HttpHeaders responseHeaders = responseEntity.getHeaders();
			List<String> list = responseHeaders.get("X-Subject-Token");
			String token=list.get(0);
//			System.out.println("取到的token:"+token);
			return token;
	}
}
