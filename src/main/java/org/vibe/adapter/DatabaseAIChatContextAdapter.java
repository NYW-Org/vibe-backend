package org.vibe.adapter;

import org.springframework.web.socket.WebSocketSession;
import org.vibe.dto.AIChatContext;

import java.util.Optional;

public class DatabaseAIChatContextAdapter implements AIChatContextAdapterStrategy {
    @Override
    public Optional<AIChatContext> adapt(WebSocketSession webSocketSession, String message) {
        return Optional.empty();
    }
}
