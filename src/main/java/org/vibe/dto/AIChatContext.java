package org.vibe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vibe.enums.AIGoal;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIChatContext {
    @JsonProperty("session_id")
    private String sessionId;

    @JsonProperty("current_goal")
    private AIGoal currentGoal;

    @JsonProperty("request_data")
    private Map<AIGoal, Object> requestData;

    @JsonProperty("conversation_history")
    private List<AIChatMessageDto> conversationHistory;

    @JsonProperty("attempt_count")
    private int attemptCount;
}
