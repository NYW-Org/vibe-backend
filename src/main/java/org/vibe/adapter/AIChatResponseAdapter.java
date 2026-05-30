package org.vibe.adapter;

import org.springframework.stereotype.Component;
import org.vibe.dto.AIChatContext;
import org.vibe.dto.AIChatMessageDto;
import org.vibe.dto.response.AIModelResponse;
import org.vibe.enums.AIGoal;
import org.vibe.enums.AIRole;

import java.util.Map;

@Component
public class AIChatResponseAdapter {

  public void adapt(AIModelResponse aiModelResponse, AIChatContext aiChatContext) {
    if (aiModelResponse.isGoalCompleted()) {
      aiChatContext.setCurrentGoal(aiModelResponse.getNextGoal());
      aiChatContext.setAttemptCount(1);
      updateRequestedData(aiModelResponse.getExtractedData(), aiChatContext.getRequestData());
    }
    aiChatContext
        .getConversationHistory()
        .add(
            AIChatMessageDto.builder()
                .role(AIRole.ASSISTANT)
                .message(aiModelResponse.getAssistantMessage())
                .build());
  }

  private void updateRequestedData(
      Map<AIGoal, Object> extractedData, Map<AIGoal, Object> requestData) {
    extractedData.forEach(
        (key, value) -> {
          if (requestData.containsKey(key)) {
            requestData.put(key, value);
            System.out.println("🔄 Synced value for registered AI Goal: " + key + " -> " + value);
          }
        });
  }
}
