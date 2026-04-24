package org.dating.dao;

import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.dating.model.User;
import org.dating.registry.UserQueryRegistry;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserDAO {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Integer save(User user) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(UserQueryRegistry.CREATE_USER, params, keyHolder, new String[] {"id"});

        Number generatedId = keyHolder.getKey();

        return generatedId.intValue();
    }

    public boolean existsByPhoneNumber(Map<String, String> phoneNumberMap) {
        return Boolean.TRUE.equals(
                namedParameterJdbcTemplate.queryForObject(UserQueryRegistry.CHECK_USER_EXISTS, phoneNumberMap, boolean.class)
        );
    }
}
