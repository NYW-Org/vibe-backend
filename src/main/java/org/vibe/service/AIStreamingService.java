package org.vibe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
                        String contextPayload = new ObjectMapper().writeValueAsString(
                                context
                        );
                        chunks.accept(contextPayload);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
