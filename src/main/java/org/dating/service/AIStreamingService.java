package org.dating.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Service
public class AIStreamingService {
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void stream(Consumer<String> chunks) {
        executorService.submit(() -> {
            try {
                List<String> response = List.of("Hello", "India", "Welcome", "to", "new", "social", "media");

                for (String text : response) {
                    Thread.sleep(150);
                    chunks.accept(text);
                }
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        });
    }
}



