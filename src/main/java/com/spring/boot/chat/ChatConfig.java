package com.spring.boot.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class ChatConfig implements WebSocketMessageBrokerConfigurer{

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // STOMP 접속 주소 URL
        registry.addEndpoint("/ws-stomp").withSockJS(); //SocketJS를 연결
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메세지 구독하는 요청 url -> 메세지 받을때
        registry.enableSimpleBroker("/sub");
        // 메세지를 보낼때 
        registry.setApplicationDestinationPrefixes("/pub");
    }

}
