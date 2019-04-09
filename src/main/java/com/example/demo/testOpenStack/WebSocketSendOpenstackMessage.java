package com.example.demo.testOpenStack;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.token.OpenstackToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@Component
public class WebSocketSendOpenstackMessage {
	@Autowired
	private GetCPUMessageFromOpenStack getCPUMessageFromOpenStack;
	@Autowired
	private GetNodeMessageFromK8s getNodeMessageFromK8s;
	@Autowired
	private GetDiskFromCeph getDiskFromCeph;
	@Autowired
    private SimpMessagingTemplate template;
	
	private HashMap<String,Object> result;
	private ObjectMapper objectMapper=new ObjectMapper();
	@Scheduled(fixedRate = 3000)
	public String sendMessage() throws Exception {
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		getCPUMessageFromOpenStack.getCPUMessageFromOpenStack();
//		getDiskFromCeph.getDiskFromCeph();
		getNodeMessageFromK8s.getNodeMessageFromK8s();
		getDiskFromCeph.getDiskFromCeph();
		result = OpenstackToken.getOpenstackMessage();
		String json = objectMapper.writeValueAsString(result);
		//轮询发送
		template.convertAndSend("/topic/hello", json);
		return json;
		
	}
}
