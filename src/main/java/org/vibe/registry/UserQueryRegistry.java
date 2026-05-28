package org.vibe.registry;

public final class UserQueryRegistry {

    public static final String CREATE_USER = """
            INSERT INTO users (name, phone_number, dob, age, profile_image, created_at, country_code) 
            VALUES (:name, :phoneNumber, :dob, :age, :profileImage, :createdAt, :countryCode)
            """;

    public static final String CHECK_USER_EXISTS = """
            SELECT EXISTS (SELECT 1 FROM users WHERE phone_number = :phoneNumber)
            """;

}
