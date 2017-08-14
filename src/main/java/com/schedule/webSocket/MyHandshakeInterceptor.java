package com.schedule.webSocket;

import com.schedule.entity.Users;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by dell on 2017/7/31.
 */
public class MyHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        if(request instanceof ServletServerHttpRequest)
        {
            System.out.println("beforeHandhake");
            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletServerHttpRequest.getServletRequest().getSession(false);
            if(session!=null){
                Users users=(Users) session.getAttribute("users");
                attributes.put("userid",users.getUserid());
                System.out.println("beforeHandshake:"+attributes.get("userid"));
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        System.out.println("afterHandhake");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
