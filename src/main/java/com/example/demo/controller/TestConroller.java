package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.testOpenStack.GetCPUMessageFromOpenStack;
import com.example.demo.testOpenStack.GetDiskFromCeph;
import com.example.demo.testOpenStack.GetMessageFromOpenStack;
import com.example.demo.testOpenStack.GetNodeMessageFromK8s;
import com.example.demo.testOpenStack.WebSocketSendOpenstackMessage;

@RestController
public class TestConroller {
	@Autowired
	private GetMessageFromOpenStack getMessageFromOpenStack;
	@Autowired
	private GetCPUMessageFromOpenStack getCPUMessageFromOpenStack;
	@Autowired
	private GetNodeMessageFromK8s getNodeMessageFromK8s;
	@Autowired
	private GetDiskFromCeph getDiskFromCeph;

	@Autowired
	private WebSocketSendOpenstackMessage webSocketSendOpenstackMessage;
//	private HashMap<String,Object> resutl;
//	private ObjectMapper objectMapper=new ObjectMapper();
	
	
	@GetMapping("/token")
	public String getTokenFromOpenStack() throws Exception {
		getMessageFromOpenStack.getMessageFromOpenStack();
		return "获取token";
	}
	@GetMapping("/cpu")
	public String getCPUTokenFromOpenStack() throws Exception {
		getCPUMessageFromOpenStack.getCPUMessageFromOpenStack();
		return "获取openstack的cpu，内存信息:";
	}
	@GetMapping("/k8s")
	public String getNodeMessageFromK8s() throws Exception {
		getNodeMessageFromK8s.getNodeMessageFromK8s();
		return "获取k8s信息成功";
	}
	@GetMapping("/ceph")
	public String getDiskFromCeph() throws Exception {
		getDiskFromCeph.getDiskFromCeph();
		return "获取ceph的后端池信息成功";
	}
	@GetMapping("/openstack")
	public String getOpenstackMessage() throws Exception {
		
		return webSocketSendOpenstackMessage.sendMessage();
	}
	@SubscribeMapping("/topic/hello")
	public String webSocket	() throws Exception {
		return webSocketSendOpenstackMessage.sendMessage();
	}
	
}
