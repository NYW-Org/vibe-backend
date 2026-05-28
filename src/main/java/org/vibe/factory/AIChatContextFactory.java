package org.vibe.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.vibe.adapter.DatabaseAIChatContextAdapter;
import org.vibe.adapter.ManualAIChatContextAdapter;
import org.vibe.adapter.RedisAIChatContextAdapter;
import org.vibe.dto.AIChatContext;
import org.vibe.exception.AIChatException;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AIChatContextFactory {
  private final RedisAIChatContextAdapter redisAIChatContextAdapter;
  private final DatabaseAIChatContextAdapter databaseAIChatContextAdapter;
  private final ManualAIChatContextAdapter manualAIChatContextAdapter;

  public AIChatContext getAIChatContext(WebSocketSession webSocketSession) {
    Optional<AIChatContext> redisContext =
        redisAIChatContextAdapter.adapt(webSocketSession);
    if (redisContext.isPresent()) {
      return redisContext.get();
    }

    Optional<AIChatContext> databaseContext =
        databaseAIChatContextAdapter.adapt(webSocketSession);
    if (databaseContext.isPresent()) {
      return databaseContext.get();
    }

    Optional<AIChatContext> manualContext =
        manualAIChatContextAdapter.adapt(webSocketSession);
    if (manualContext.isPresent()) {
      return manualContext.get();
    }

    throw new AIChatException(
        "Critical failure: Failed to resolve or initialize an AI Chat Context for session: "
            + webSocketSession.getId());
  }

  public AIChatContext convetToChatContext(TextMessage textMessage) {
    return manualAIChatContextAdapter.adaptTextMessage(textMessage.getPayload()).get();
  }

}
