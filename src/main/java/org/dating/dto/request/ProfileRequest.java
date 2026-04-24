package org.dating.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "phone number is required")
    @Pattern(regexp = "^\\+91\\d{10}$", message = "invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "date of birth is required")
    private String dob;
    @NotNull(message = "Age is required")
    @Min(value = 18, message = "user must be 18+")
    private Integer age;
    private String profileImage;
    private String createdAt;
    private String updatedAt;
    @NotBlank(message = "country code is required")
    private String countryCode;
}
