package org.vibe.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum AIGoal {
    @JsonProperty("lang_pref")
    LANG_PREF(0),

    @JsonProperty("display_name")
    DISPLAY_NAME(1),

    @JsonProperty("age")
    AGE(2);

    private final int priority;

    AIGoal(int priority) {
        this.priority = priority;
    }
}
