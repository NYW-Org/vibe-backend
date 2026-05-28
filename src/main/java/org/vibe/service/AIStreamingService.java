package org.vibe.service;

import org.springframework.stereotype.Service;
import org.vibe.dto.AIChatContext;
import org.vibe.dto.AIChatMessageDto;
import org.vibe.enums.AIGoal;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Service
public class AIStreamingService {
  private final ExecutorService executorService = Executors.newFixedThreadPool(10);

  public void stream(AIChatContext context, Consumer<String> chunks) {
    executorService.submit(
        () -> {
          try {
            if (context.getCurrentGoal() == AIGoal.LANG_PREF) {
              String selectionPayload =
                  "{"
                      + "\"type\":\"SELECTION_REQUIRED\","
                      + "\"message\":\"Please select your preferred language:\","
                      + "\"options\":[\"English\", \"Hindi\"]"
                      + "}";

              chunks.accept(selectionPayload);
            } else {
              List<AIChatMessageDto> history = context.getConversationHistory();

              if (history != null && !history.isEmpty()) {
                AIChatMessageDto lastMessageDto = history.get(history.size() - 1);
                String rawMessage = lastMessageDto.getMessage();

                if (rawMessage != null && !rawMessage.isBlank()) {
                  String[] tokens = rawMessage.split("(?<=\\s)|(?=\\s)");

                  for (String token : tokens) {
                    Thread.sleep(100);
                    chunks.accept(token);
                  }
                }
              }
            }
          } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
          }
        });
  }
}
