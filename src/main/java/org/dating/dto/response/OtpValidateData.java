package org.dating.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OtpValidateData {
    private String verificationId;
    private String verificationStatus;
    private String mobileNumber;
    private String errorMessage;
}
