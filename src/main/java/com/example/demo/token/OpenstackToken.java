package com.example.demo.token;

import java.util.HashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.testOpenStack.GetMessageFromOpenStack;
@Component
public class OpenstackToken {
	private OpenstackToken() {};
	private static String tokenMessage;
	private static HashMap<String, Object> openstackMessage;
	private static GetMessageFromOpenStack getMessageFromOpenStack=new GetMessageFromOpenStack();
	public static String getTokenMessage() throws Exception {
		if(tokenMessage==null) {
			tokenMessage=getMessageFromOpenStack.getMessageFromOpenStack();
		}
		return tokenMessage;
	}
	public static HashMap<String, Object> getOpenstackMessage() {
		if(openstackMessage==null) {
			openstackMessage=new HashMap<>();
		}
		return openstackMessage ;
	}
	@Scheduled(cron="0 0/50 * * * *")
	public void scheduleDeleteToken() {
//		System.out.println("删除token定时任务开始执行");
		if(tokenMessage!=null) {
			tokenMessage=null;
		}
	}
}
