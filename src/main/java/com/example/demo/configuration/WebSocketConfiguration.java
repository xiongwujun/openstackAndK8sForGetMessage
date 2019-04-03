
package com.example.demo.configuration;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    /**
     * 注册Stomp端点
     * @param registry 注册机
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    	//setAllowedOrigins("*")指的是允许所有的IP地址访问
        registry.addEndpoint("/endpoint-websocket").setAllowedOrigins("*").withSockJS();
    }

    /**
     * 配置消息代理
     * @param registry 注册机
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 启用简单代理
        registry.enableSimpleBroker("/topic","/user");
        // 连接前缀
        registry.setApplicationDestinationPrefixes("/app");
        //这句话表示给指定用户发送一对一的主题前缀是"/user"
        registry.setUserDestinationPrefix("/user");
    }

	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
		// TODO Auto-generated method stub
		return false;
	}

}

