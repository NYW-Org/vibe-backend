package org.vibe.adapter;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.vibe.dto.AIChatContext;

import java.util.Optional;

@Component
public class RedisAIChatContextAdapter implements AIChatContextAdapterStrategy {
    @Override
    public Optional<AIChatContext> adapt(WebSocketSession webSocketSession, String message) {
        return Optional.empty();
    }
}
