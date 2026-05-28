package org.vibe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.vibe.enums.AIRole;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AIChatMessageDto {
    private AIRole role;
    private String message;
    private List<String> hint;
}
