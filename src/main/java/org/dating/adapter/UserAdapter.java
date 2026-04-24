package org.dating.adapter;

import org.dating.dto.request.ProfileRequest;
import org.dating.model.User;
import org.dating.util.DateTimeUtil;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserAdapter {
    public User adaptProfileRequest(ProfileRequest profileRequest) {
        OffsetDateTime createdAt = DateTimeUtil.parseOrDefault(
                profileRequest.getCreatedAt(),
                OffsetDateTime.now()
        );
        return User.builder()
                .age(profileRequest.getAge())
                .dob(profileRequest.getDob())
                .name(profileRequest.getName())
                .profileImage(profileRequest.getProfileImage())
                .phoneNumber(profileRequest.getPhoneNumber())
                .createdAt(createdAt)
                .countryCode(profileRequest.getCountryCode())
                .build();

    }

    public Map<String, String> adaptForPhoneMap(ProfileRequest profileRequest) {
        return Map.of("phoneNumber", profileRequest.getPhoneNumber());
    }
}
