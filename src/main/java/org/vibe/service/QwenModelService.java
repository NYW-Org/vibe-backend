package org.vibe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vibe.dto.AIChatContext;
import org.vibe.dto.response.AIModelResponse;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class QwenModelService {

  private final RestTemplate restTemplate;

  public AIModelResponse fetchChatResponse(AIChatContext aiChatContext) {
    ResponseEntity<AIModelResponse> aiModelResponse =
        restTemplate.exchange(
            "", HttpMethod.POST, getHttpEntity(aiChatContext), AIModelResponse.class);
    return Objects.requireNonNull(aiModelResponse.getBody());
  }

  private HttpEntity<AIChatContext> getHttpEntity(AIChatContext aiChatContext) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(java.util.Collections.singletonList(MediaType.APPLICATION_JSON));
    return new HttpEntity<>(aiChatContext, headers);
  }
}
