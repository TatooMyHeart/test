package com.schedule.webSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by dell on 2017/7/30.
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(systemWebSocketHandler(),"/webSocketServer");
        System.out.println("regidter!");
        registry.addHandler(systemWebSocketHandler(),"/sockjs/webSocketServer")
                .addInterceptors(new MyHandshakeInterceptor()).setAllowedOrigins("*").withSockJS();
    }

    @Bean
    public WebSocketHandler systemWebSocketHandler(){
        return new SystemWebSocketHandler();
    }
}
