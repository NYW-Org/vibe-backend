package org.vibe.factory;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
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

  public AIChatContext getAIChatContext(WebSocketSession webSocketSession, String message) {
    Optional<AIChatContext> redisContext =
        redisAIChatContextAdapter.adapt(webSocketSession, message);
    if (redisContext.isPresent()) {
      return redisContext.get();
    }

    Optional<AIChatContext> databaseContext =
        databaseAIChatContextAdapter.adapt(webSocketSession, message);
    if (databaseContext.isPresent()) {
      return databaseContext.get();
    }

    Optional<AIChatContext> manualContext =
        manualAIChatContextAdapter.adapt(webSocketSession, message);
    if (manualContext.isPresent()) {
      return manualContext.get();
    }

    throw new AIChatException(
        "Critical failure: Failed to resolve or initialize an AI Chat Context for session: "
            + webSocketSession.getId());
  }
}
