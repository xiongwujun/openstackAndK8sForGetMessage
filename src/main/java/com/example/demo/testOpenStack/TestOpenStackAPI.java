package com.example.demo.testOpenStack;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
/**
 * spring使用restTemplate
 * @author Administrator
 *
 */
public class TestOpenStackAPI {
	//用于远程调用
	private TestOpenStackAPI() {};
	private static RestTemplate restTemplate;
	public static void configRestTemplate() {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
		messageConverters.add(new MappingJackson2HttpMessageConverter());
		restTemplate=new RestTemplate();
		restTemplate.setMessageConverters(messageConverters);
		
	}
	public static RestTemplate getRestTemplate(	) {
		TestOpenStackAPI.configRestTemplate();
//		System.out.println("restTemplate创建完毕");
		return restTemplate;
	}
}
