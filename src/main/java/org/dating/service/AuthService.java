package org.dating.service;

import lombok.RequiredArgsConstructor;
import org.dating.dto.request.OtpRequest;
import org.dating.dto.request.ValidateOtpRequest;
import org.dating.dto.response.MessageValidateResponse;
import org.dating.dto.response.OtpDataResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtService jwtService;
  private final MessageCentralService messageCentralService;

  @Value("${default.phone-number}")
  private String adminPhone;

  @Value("${default.role}")
  private String adminRole;

  public String validateUser(ValidateOtpRequest validateOtpRequest) {
    MessageValidateResponse messageValidateResponse =
        messageCentralService.validateOtp(validateOtpRequest);
    if (messageValidateResponse.getResponseCode() != 200) {
      return null;
    }
    return jwtService.generateToken(
        messageValidateResponse.getData().getMobileNumber(), "User");
  }

  public OtpDataResponse sendOtp(OtpRequest otpRequest) {
    return messageCentralService.sendOtp(otpRequest.getPhoneNumber());
  }

  public String generateToken(String phoneNumber) {
    return jwtService.generateToken(phoneNumber, "User");
  }
}
