package org.vibe.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.vibe.dto.AIChatContext;
import org.vibe.dto.AIChatMessageDto;
import org.vibe.dto.AIUserData;
import org.vibe.enums.AIGoal;
import org.vibe.enums.AIRole;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ManualAIChatContextAdapter implements AIChatContextAdapterStrategy {
  private final ObjectMapper objectMapper;

  @Override
  public Optional<AIChatContext> adapt(WebSocketSession webSocketSession) {
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

  public Optional<AIChatContext> adaptTextMessage(String message) {
    try {
      return Optional.of(objectMapper.readValue(message, AIChatContext.class));
    } catch (JsonProcessingException e) {
      return Optional.empty();
    }
  }
}
