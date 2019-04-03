package com.example.demo.testOpenStack;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.besjon.pojo.Hypervisors;
import com.besjon.pojo.JsonRootBean;
import com.example.demo.token.OpenstackToken;
import com.example.utils.PropertyUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
@PropertySource(value="classpath:openstack.properties")
@Component
public class GetCPUMessageFromOpenStack {
	
	private String os_hypervisors_url=PropertyUtil.getProperty("os_hypervisors_url");
	private HashMap<String,Object> openstackMessage = OpenstackToken.getOpenstackMessage();
	public void getCPUMessageFromOpenStack() {
		try {
			RestTemplate restTemplate = TestOpenStackAPI.getRestTemplate();
			URI uri=new URI(os_hypervisors_url);
			PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
			pollingConnectionManager.setMaxTotal(1000);
			pollingConnectionManager.setDefaultMaxPerRoute(1000);
			HttpClientBuilder httpClientBuilder = HttpClients.custom();
			httpClientBuilder.setConnectionManager(pollingConnectionManager);
			List<Header> headers = new ArrayList<>();
			headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
			headers.add(new BasicHeader("Accept-Language", "zh-CN"));
			headers.add(new BasicHeader("Connection", "Keep-Alive"));
			headers.add(new BasicHeader("X-Auth-Token", OpenstackToken.getTokenMessage()));
			httpClientBuilder.setDefaultHeaders(headers);
			HttpClient httpClient = httpClientBuilder.build();
			HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
			restTemplate.setRequestFactory(clientHttpRequestFactory);
			ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
			String message = responseEntity.getBody();
			ObjectMapper mapper=new ObjectMapper();
			JsonRootBean value = mapper.readValue(message.getBytes(), JsonRootBean.class);
			List<Hypervisors> hypervisors = value.getHypervisors();
			Iterator<Hypervisors> iterator = hypervisors.iterator();
			int local_gb_used=0;
			int vcpus=0;
			int memory_mb_used=0;
			int memory_mb=0;
			long disk_available_least=0;
//			long free_disk_gb=0;
			int vcpus_used=0;
			while(iterator.hasNext()) {
				Hypervisors node = iterator.next();
				vcpus +=node.getVcpus();
				local_gb_used+=node.getLocal_gb_used();
				memory_mb+=node.getMemory_mb();
				memory_mb_used+=node.getMemory_mb_used();
				disk_available_least+=node.getDisk_available_least();
//				free_disk_gb+=node.getFree_disk_gb();
				vcpus_used+=node.getVcpus_used();
				
			}
			
			openstackMessage.put("openstack_used_disk", local_gb_used);
			openstackMessage.put("openstack_vcpus", vcpus);
			openstackMessage.put("openstack_used_vcpus", vcpus_used);
			openstackMessage.put("openstack_memory_request", memory_mb_used/1024);
			openstackMessage.put("openstack_memory_capacity", memory_mb/1024);
			openstackMessage.put("openstack_total_disk", disk_available_least);
//			openstackMessage.put("free_disk_gb", free_disk_gb);
			
//			System.out.println("集群可以使用的vcpu总数"+vcpus);
//			System.out.println("集群已经使用的vcpu总数"+local_gb_used);
//			System.out.println("集群可以使用的内存大小"+memory_mb);
//			System.out.println("集群已经使用的内存大小"+memory_mb_used);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
