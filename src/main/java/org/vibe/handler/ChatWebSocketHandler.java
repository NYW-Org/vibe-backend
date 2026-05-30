package org.vibe.handler;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.vibe.adapter.AIChatResponseAdapter;
import org.vibe.dto.AIChatContext;
import org.vibe.dto.response.AIModelResponse;
import org.vibe.factory.AIChatContextFactory;
import org.vibe.manager.SessionManager;
import org.vibe.service.AIStreamingService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.vibe.service.QwenModelService;
import org.vibe.util.AIChatUtil;

@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final SessionManager sessionManager;
    private final AIStreamingService aiStreamingService;
    private final AIChatContextFactory aiChatContextFactory;
    private final QwenModelService qwenModelService;
    private final AIChatResponseAdapter aiChatResponseAdapter;

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession webSocketSession) {
        sessionManager.add(webSocketSession);
        AIChatContext aiChatContext = aiChatContextFactory.getAIChatContext(webSocketSession);

        if(AIChatUtil.isLastMessageUser(aiChatContext)) {
            AIModelResponse aiModelResponse = qwenModelService.fetchChatResponse(aiChatContext);
            aiChatResponseAdapter.adapt(aiModelResponse, aiChatContext);
        }

        aiStreamingService.stream(aiChatContext, chunk -> {
            sessionManager.send(webSocketSession, chunk);
        });
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession webSocketSession, CloseStatus status) {
        sessionManager.remove(webSocketSession);
    }

    @Override
    public void handleTextMessage(@NonNull WebSocketSession webSocketSession, @NonNull TextMessage message) {
        AIChatContext aiChatContext = aiChatContextFactory.convetToChatContext(message);

        AIModelResponse aiModelResponse = qwenModelService.fetchChatResponse(aiChatContext);
        aiChatResponseAdapter.adapt(aiModelResponse, aiChatContext);

        aiStreamingService.stream(aiChatContext, chunk -> {
            sessionManager.send(webSocketSession, chunk);
        });
    }

    @Override
    public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) {
        sessionManager.close(session);
    }
}
