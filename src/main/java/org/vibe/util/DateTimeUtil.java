package org.vibe.util;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    public static OffsetDateTime parseOrDefault(String value, OffsetDateTime defaultValue) {
        try {
            return (value == null || value.isBlank())
                    ? defaultValue
                    : OffsetDateTime.parse(value);
        } catch (DateTimeParseException e) {
            return defaultValue;
        }
    }
}
