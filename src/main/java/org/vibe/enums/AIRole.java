package org.vibe.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum AIRole {
    @JsonProperty("assistant")
    ASSISTANT,

    @JsonProperty("user")
    USER,

    @JsonProperty("app")
    APP
}
