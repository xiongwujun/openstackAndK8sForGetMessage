package com.example.demo.testOpenStack;

import java.math.BigInteger;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.demo.token.OpenstackToken;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;




@Component 
public class GetNodeMessageFromK8s {
	public void getNodeMessageFromK8s() throws Exception {
		RestTemplate restTemplate=TestOpenStackAPI.getRestTemplate();
//		URI uri=new URI("https://192.168.24.210:6443/api/v1/nodes/");
		URI uri2=new URI("https://192.168.24.210:31620/api/v1/node?filterBy=&itemsPerPage=10&name=&namespace=&page=1&sortBy=d,creationTimestamp");
		String auth="Bearer netlab523";
		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			@Override	
			public boolean isTrusted(X509Certificate[] chain, String authType)
					throws java.security.cert.CertificateException {

				return true;
			}
		}).build();
		HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
		SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory())
				//这里只是对于https进行不验证
				.register("https", sslSocketFactory)
				.build();
		PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		pollingConnectionManager.setMaxTotal(1000);
		pollingConnectionManager.setDefaultMaxPerRoute(1000);
		List<Header> headers = new ArrayList<>();
		headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
		headers.add(new BasicHeader("Accept-Language", "zh-CN"));
		headers.add(new BasicHeader("Connection", "Keep-Alive"));
		headers.add(new BasicHeader("Authorization", auth));
		httpClientBuilder.setSSLContext(sslContext);
		httpClientBuilder.setConnectionManager(pollingConnectionManager);
		httpClientBuilder.setDefaultHeaders(headers);
		HttpClient httpClient = httpClientBuilder.build();
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		restTemplate.setRequestFactory(clientHttpRequestFactory);
		ResponseEntity<String> response = restTemplate.getForEntity(uri2, String.class);
		String body = response.getBody();
		//		body.replaceAll("", "_");
//		Gson gson=new Gson();
//		JsonObject jObject=new JsonObject();

		//		JsonObject jsonObject = new JsonObject(body);
		JsonParser jParser=new JsonParser();
		JsonElement element = jParser.parse(body);
		JsonObject t = element.getAsJsonObject();
		//只有object和array的概念
		JsonArray array = t.getAsJsonArray("nodes");
		int k8s_cpu_request=0;
		int k8s_cpu_capacity=0;
		BigInteger k8s_memory_request=new BigInteger("0");
		BigInteger k8s_memory_capacity=new BigInteger("0");
		BigInteger Num_constant=new BigInteger("1024");
		int k8s_pod_used=0;
		int k8s_pod_capacity=0;
		for (JsonElement jsonElement : array) {
			k8s_cpu_request+=jsonElement.getAsJsonObject().getAsJsonObject("allocatedResources").get("cpuRequests").getAsInt();
			k8s_cpu_capacity+=jsonElement.getAsJsonObject().getAsJsonObject("allocatedResources").get("cpuCapacity").getAsInt();
			k8s_memory_request=k8s_memory_request.add(jsonElement.getAsJsonObject().getAsJsonObject("allocatedResources").get("memoryRequests").getAsBigInteger());
			k8s_memory_capacity=k8s_memory_capacity.add(jsonElement.getAsJsonObject().getAsJsonObject("allocatedResources").get("memoryCapacity").getAsBigInteger());
			k8s_pod_used+=jsonElement.getAsJsonObject().getAsJsonObject("allocatedResources").get("allocatedPods").getAsInt();
			k8s_pod_capacity+=jsonElement.getAsJsonObject().getAsJsonObject("allocatedResources").get("podCapacity").getAsInt();
		}
		
//		int k8s_cpu_request = t.getAsJsonArray("nodes").get(0).getAsJsonObject().getAsJsonObject("allocatedResources").get("cpuRequests").getAsInt()+t.getAsJsonArray("nodes").get(1).getAsJsonObject().getAsJsonObject("allocatedResources").get("cpuRequests").getAsInt();
//		int k8s_cpu_capacity = t.getAsJsonArray("nodes").get(0).getAsJsonObject().getAsJsonObject("allocatedResources").get("cpuCapacity").getAsInt()+t.getAsJsonArray("nodes").get(1).getAsJsonObject().getAsJsonObject("allocatedResources").get("cpuCapacity").getAsInt();
//		BigDecimal k8s_memory_request = t.getAsJsonArray("nodes").get(0).getAsJsonObject().getAsJsonObject("allocatedResources").get("memoryRequests").getAsBigDecimal().add(t.getAsJsonArray("nodes").get(1).getAsJsonObject().getAsJsonObject("allocatedResources").get("memoryRequests").getAsBigDecimal());
//		BigDecimal k8s_memory_capacity = t.getAsJsonArray("nodes").get(0).getAsJsonObject().getAsJsonObject("allocatedResources").get("memoryCapacity").getAsBigDecimal().add(t.getAsJsonArray("nodes").get(1).getAsJsonObject().getAsJsonObject("allocatedResources").get("memoryCapacity").getAsBigDecimal());
//		int k8s_pod_used = t.getAsJsonArray("nodes").get(0).getAsJsonObject().getAsJsonObject("allocatedResources").get("allocatedPods").getAsInt()+t.getAsJsonArray("nodes").get(1).getAsJsonObject().getAsJsonObject("allocatedResources").get("allocatedPods").getAsInt();
//		int k8s_pod_capacity = t.getAsJsonArray("nodes").get(0).getAsJsonObject().getAsJsonObject("allocatedResources").get("podCapacity").getAsInt()+t.getAsJsonArray("nodes").get(1).getAsJsonObject().getAsJsonObject("allocatedResources").get("podCapacity").getAsInt();
		HashMap<String,Object> map = OpenstackToken.getOpenstackMessage();
		Double k8s_memory_request_double=new Double(k8s_memory_request.divide(Num_constant).divide(Num_constant).toString());
		Double k8s_memory_capacity_double=new Double(k8s_memory_capacity.divide(Num_constant).divide(Num_constant).toString());
		map.put("k8s_cpu_request",((double)k8s_cpu_request)/1000);
		map.put("k8s_cpu_capacity",k8s_cpu_capacity/1000);
		map.put("k8s_memory_request",Math.round((k8s_memory_request_double)*Math.pow(10, 2))/Math.pow(10, 2));
		map.put("k8s_memory_capacity",Math.round((k8s_memory_capacity_double/1024)*Math.pow(10, 2))/Math.pow(10, 2));
		map.put("k8s_pod_used",k8s_pod_used);
		map.put("k8s_pod_capacity",k8s_pod_capacity);	
		
		//		JsonElement jsonElement = t.get("nodes").get;


		//		ObjectMapper objectMapper=new ObjectMapper();
		//		GsonJsonParser gson=new GsonJsonParser();
		//		Map<String, Object> map = gson.parseMap(body);
		//		Set<Entry<String,Object>> entrySet = map.entrySet();
		//		for (Entry<String, Object> entry : entrySet) {
		//			System.out.println(entry.getKey()+"--------------------"+entry.getValue());
	}
	//		System.out.println("得到的"+body);
}
