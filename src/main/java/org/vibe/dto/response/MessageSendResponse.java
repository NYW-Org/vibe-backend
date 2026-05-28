package org.vibe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageSendResponse {
  private int responseCode;
  private String message;
  private OtpDataResponse data;
}
