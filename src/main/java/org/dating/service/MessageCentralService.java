package org.dating.service;

import lombok.RequiredArgsConstructor;
import org.dating.dto.request.ValidateOtpRequest;
import org.dating.dto.response.MessageSendResponse;
import org.dating.dto.response.MessageTokenResponse;
import org.dating.dto.response.MessageValidateResponse;
import org.dating.dto.response.OtpDataResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageCentralService {

  private final RestTemplate restTemplate;

  @Value("${otp-service.key}")
  private String key;

  @Value("${otp-service.base-url}")
  private String baseUrl;

  @Value("${otp-service.customer-id}")
  private String customerId;

  @Value("${otp-service.email-id}")
  private String emailId;

  @Value("${otp-service.token-path}")
  private String tokenPath;

  @Value("${otp-service.send-otp-path}")
  private String sendOtpPath;

  @Value("${otp-service.validate-path}")
  private String validatePath;

  public String getAuthToken() {
    ResponseEntity<MessageTokenResponse> response =
        restTemplate.getForEntity(generateTokenUri(), MessageTokenResponse.class);
    return Objects.requireNonNull(response.getBody()).getToken();
  }

  public OtpDataResponse sendOtp(String phoneNumber) {
    ResponseEntity<MessageSendResponse> response =
        restTemplate.exchange(
            generateSendOtpUri(phoneNumber),
            HttpMethod.POST,
            getHttpEntity(),
            MessageSendResponse.class);
    return Objects.requireNonNull(response.getBody()).getData();
  }

  public MessageValidateResponse validateOtp(ValidateOtpRequest validateOtpRequest) {
    ResponseEntity<MessageValidateResponse> response = restTemplate.exchange(generateValidateOtpUri(validateOtpRequest), HttpMethod.GET, getHttpEntity(), MessageValidateResponse.class);
    return response.getBody();
  }

  private String generateTokenUri() {
    String encodedKey = Base64.getEncoder().encodeToString(key.getBytes(StandardCharsets.UTF_8));
    return UriComponentsBuilder.fromHttpUrl(baseUrl)
        .path(tokenPath)
        .queryParam("customerId", customerId)
        .queryParam("key", encodedKey)
        .queryParam("scope", "NEW")
        .queryParam("country", "91")
        .queryParam("email", emailId)
        .toUriString();
  }

  private String generateSendOtpUri(String phoneNumber) {
    return UriComponentsBuilder.fromHttpUrl(baseUrl)
        .path(sendOtpPath)
        .queryParam("countryCode", "91")
        .queryParam("mobileNumber", phoneNumber)
        .queryParam("customerId", customerId)
        .queryParam("flowType", "SMS")
        .toUriString();
  }

  private String generateValidateOtpUri(ValidateOtpRequest validateOtpRequest) {
    return UriComponentsBuilder.fromHttpUrl(baseUrl)
      .path(validatePath)
      .queryParam("countryCode", "91")
      .queryParam("mobileNumber", validateOtpRequest.getPhoneNumber())
      .queryParam("verificationId", validateOtpRequest.getVerificationId())
      .queryParam("customerId", customerId)
      .queryParam("code", validateOtpRequest.getCode())
      .toUriString();
  }

  private HttpEntity<String> getHttpEntity() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("authToken", getAuthToken());
    headers.set("User-Agent", "Mozilla/5.0");
    headers.setAccept(java.util.Collections.singletonList(MediaType.APPLICATION_JSON));
    return new HttpEntity<>(headers);
  }
}
