package org.vibe.adapter;

import org.springframework.web.socket.WebSocketSession;
import org.vibe.dto.AIChatContext;
import org.vibe.dto.AIChatMessageDto;
import org.vibe.dto.AIUserData;
import org.vibe.enums.AIGoal;
import org.vibe.enums.AIRole;

import java.util.List;
import java.util.Optional;

public class ManualAIChatContextAdapter implements AIChatContextAdapterStrategy {
  @Override
  public Optional<AIChatContext> adapt(WebSocketSession webSocketSession, String message) {
    AIChatMessageDto aiChatMessageDto =
        AIChatMessageDto.builder()
            .role(AIRole.APP)
            .message("What is your preferred lang?")
            .hint(List.of("English"))
            .build();

    return Optional.of(
        AIChatContext.builder()
            .sessionId(webSocketSession.getId())
            .currentGoal(AIGoal.LANG_PREF)
            .requestData(new AIUserData())
            .conversationHistory(List.of(aiChatMessageDto))
            .attemptCount(1)
            .build());
  }
}
