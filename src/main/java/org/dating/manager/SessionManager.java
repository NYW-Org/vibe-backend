package org.dating.manager;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void add(WebSocketSession webSocketSession) {
        sessions.put(webSocketSession.getId(), webSocketSession);
    }

    public void remove(WebSocketSession webSocketSession) {
        sessions.remove(webSocketSession.getId());
    }

    public void send(WebSocketSession webSocketSession, String message) {
        if(webSocketSession.isOpen()) {
            try {
                webSocketSession.sendMessage(new TextMessage(message));
            } catch (IOException exception) {
                close(webSocketSession);
            }
        }
    }

    public void close(WebSocketSession webSocketSession) {
        try {
            webSocketSession.close();
        } catch (IOException exception) {}
    }
}
