package org.dating.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateOtpRequest {
    @NonNull
    private String verificationId;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String code;
}
