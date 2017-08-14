package com.schedule.webSocket;

import com.google.gson.Gson;
import com.schedule.paramterBody.UsersBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/30.
 */
@RequestMapping("/myWebsocket")
public class SystemWebSocketHandler implements WebSocketHandler {
    private static final Map<Integer,WebSocketSession> clients;

    static {
        clients = new HashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("ConnectionEstablished");
        Integer userid=(Integer)session.getAttributes().get("userid");
        System.out.println("usersid="+userid);
        if(userid!=null)
        {
            clients.put(userid,session);
            System.out.println("users.length="+clients.size());
            session.sendMessage(new TextMessage("ConnectionEstablished"));
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

        if(session.isOpen()){
            session.close();
        }
        System.out.println("handleTransportError");
        clients.remove((Integer)session.getAttributes().get("userid"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("ConnectionClosed");
        clients.remove((Integer)session.getAttributes().get("userid"));

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public boolean sendMessageToExcutor(Integer userid, TextMessage message)
    {
        if(clients.get(userid)==null){return false;}
        WebSocketSession session = clients.get(userid);
        if(!session.isOpen()){return false;}
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
