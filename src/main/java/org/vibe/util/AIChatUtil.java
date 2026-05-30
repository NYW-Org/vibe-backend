package org.vibe.util;

import org.vibe.dto.AIChatContext;
import org.vibe.dto.AIChatMessageDto;
import org.vibe.enums.AIRole;

public class AIChatUtil {

    public static boolean isLastMessageUser(AIChatContext aiChatContext) {
        AIChatMessageDto aiChatMessageDto = aiChatContext.getConversationHistory().getLast();
        return aiChatMessageDto.getRole().equals(AIRole.USER);
    }
}
