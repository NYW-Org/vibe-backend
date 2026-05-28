package org.vibe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.vibe.dto.AIChatContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Service
public class AIStreamingService {
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final ObjectMapper objectMapper;

    public AIStreamingService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void stream(AIChatContext context, Consumer<String> chunks) {
        executorService.submit(
                () -> {
                    try {
                        String contextPayload = objectMapper.writeValueAsString(context);
                        chunks.accept(contextPayload);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
