package org.vibe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String phoneNumber;
    private String dob;
    private Integer age;
    private String profileImage;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private String countryCode;
}
