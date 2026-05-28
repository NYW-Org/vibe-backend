package org.vibe.adapter;

import org.vibe.dto.request.ProfileRequest;
import org.vibe.model.User;
import org.vibe.util.DateTimeUtil;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
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
