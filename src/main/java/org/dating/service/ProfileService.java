package org.dating.service;

import lombok.RequiredArgsConstructor;
import org.dating.adapter.UserAdapter;
import org.dating.dao.UserDAO;
import org.dating.dto.request.ProfileRequest;
import org.dating.dto.response.ProfileResponse;
import org.dating.enums.ResponseType;
import org.dating.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserDAO userDAO;
    private final UserAdapter userAdapter;

    public ProfileResponse createProfile(ProfileRequest profileRequest) {
        User user = userAdapter.adaptProfileRequest(profileRequest);
        try {
            Integer userId = userDAO.save(user);
            return ProfileResponse.builder()
                    .type(ResponseType.SUCCESS.name())
                    .message("User created successfully")
                    .userId(String.valueOf(userId))
                    .build();
        } catch (DataIntegrityViolationException ex) {
            return ProfileResponse.builder()
                    .type(ResponseType.ERROR.name())
                    .message(ex.getMessage())
                    .build();
        }
    }

}
