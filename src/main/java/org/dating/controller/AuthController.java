package org.dating.controller;

import lombok.RequiredArgsConstructor;
import org.dating.dto.request.ValidateOtpRequest;
import org.dating.dto.response.LoginResponse;
import org.dating.dto.request.OtpRequest;
import org.dating.dto.response.OtpDataResponse;
import org.dating.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/validate-otp")
  public ResponseEntity<LoginResponse> login(@RequestBody ValidateOtpRequest validateOtpRequest) {
    String token = authService.validateUser(validateOtpRequest);
    if (token == null || token.isEmpty()) {
      LoginResponse.builder().message("Number not found");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(LoginResponse.builder().message("Number not found").build());
    }
    return ResponseEntity.ok(LoginResponse.builder().token(token).message("Login Successful").build());
  }

  @PostMapping("/send-otp")
  public ResponseEntity<OtpDataResponse> sendOtp(@RequestBody OtpRequest otpRequest) {
    OtpDataResponse otpDataResponse = authService.sendOtp(otpRequest);
    if(otpDataResponse.getVerificationId() == null ) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(otpDataResponse);
    }
    return ResponseEntity.ok(otpDataResponse);
  }
}
