package org.vibe.handler;

import lombok.RequiredArgsConstructor;
import org.vibe.manager.SessionManager;
import org.vibe.service.AIStreamingService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final SessionManager sessionManager;
    private final AIStreamingService aiStreamingService;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        sessionManager.add(webSocketSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus status) {
        sessionManager.remove(webSocketSession);
    }

    @Override
    public void handleTextMessage(WebSocketSession webSocketSession, TextMessage message) {
        aiStreamingService.stream(chunk -> {
            sessionManager.send(webSocketSession, chunk);
        });
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        sessionManager.close(session);
    }
}
