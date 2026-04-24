package org.dating.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dating.dto.request.ProfileRequest;
import org.dating.dto.response.ProfileResponse;
import org.dating.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ProfileResponse> createProfile(@Valid @RequestBody ProfileRequest profileRequest) {
        ProfileResponse profileResponse = profileService.createProfile(profileRequest);
        return ResponseEntity.ok(profileResponse);
    }

}
