package org.dating.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OtpDataResponse {
  private String verificationId;
  private String mobileNumber;
}
