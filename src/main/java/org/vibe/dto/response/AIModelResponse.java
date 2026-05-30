package org.vibe.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.vibe.enums.AIGoal;

import java.util.Map;

@Data
public class AIModelResponse {
    @JsonProperty("assistant_message")
    private String assistantMessage;

    @JsonProperty("goal_completed")
    private boolean goalCompleted;

    @JsonProperty("extracted_data")
    private Map<AIGoal, Object> extractedData;

    @JsonProperty("next_goal")
    private AIGoal nextGoal;
}
