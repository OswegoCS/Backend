package com.csc380.codepeerreview.repositories.implementation;

import com.csc380.codepeerreview.models.User;
import com.csc380.codepeerreview.repositories.dao.UserDao;
import com.csc380.codepeerreview.repositories.mappers.UserRowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    private final String SELECT_BY_ID = """
    SELECT id, screen_name, first_name, last_name, email 
    FROM users
    WHERE id = :id""";

    private final String SELECT_BY_EMAIL = """
    SELECT id, screen_name, first_name, last_name, email
    FROM users 
    WHERE email = :email""";

    private String insertUsers = 
    "INSERT INTO users (first_name, last_name, email, screen_name, role) VALUES ";

    private final NamedParameterJdbcTemplate template;
    private final RowMapper<User> userMapper;

    @Autowired
    public UserDaoImpl(NamedParameterJdbcTemplate template, UserRowMapper userMapper) {
        this.template = template;
        this.userMapper = userMapper;
    }

    @Override
    public User findById(Integer id) {
        return template.queryForObject(
            SELECT_BY_ID, new MapSqlParameterSource("id", id), userMapper);
    }

    @Override
    public void insertUsers(List<User> users, String type) {

        StringBuilder query = new StringBuilder(insertUsers);
        String firstName = "first_name";
        String lastName = "last_name";
        String email = "email";
        String screenName = "screen_name";
        String role = "role";
        Map<String, Object> headers = new HashMap<String, Object>();
        SqlParameterSource param;

        for (int i = 0; i < users.size(); i++) {
            String index = String.valueOf(i);
            headers.put(firstName.concat(index), users.get(i).getFirstName());
            headers.put(lastName.concat(index), users.get(i).getLastName());
            headers.put(email.concat(index), users.get(i).getEmail());
            headers.put(screenName.concat(index), users.get(i).getEmail().split("@")[0]);
            headers.put(role.concat(index), type);

            query.append("(")
            .append(":").append(firstName.concat(index))
            .append(", ").append(":").append(lastName.concat(index))
            .append(", ").append(":").append(email.concat(index))
            .append(", ").append(":").append(screenName.concat(index))
            .append(", ").append(":").append(role.concat(index))
            .append(") ")
            .append(i < (users.size() - 1) ? ", " : "");
        }

        param = new MapSqlParameterSource(headers);

        template.update(query.toString(), param);

    }

    @Override
    public User findByEmail(String email) {
        try {
            return template.queryForObject(
                SELECT_BY_EMAIL, new MapSqlParameterSource("email", email), userMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
