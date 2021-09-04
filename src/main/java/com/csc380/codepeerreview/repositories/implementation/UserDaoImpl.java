package com.csc380.codepeerreview.repositories.implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csc380.codepeerreview.models.User;
import com.csc380.codepeerreview.repositories.dao.UserDao;
import com.csc380.codepeerreview.repositories.mappers.IdRowMapper;
import com.csc380.codepeerreview.repositories.mappers.UserRowMapper;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    private final String SELECT_BY_ID = "SELECT id, screen_name, first_name, last_name, email FROM users WHERE id = :id";
    private final String SELECT_BY_EMAIL = "SELECT id, screen_name, first_name, last_name, email FROM users WHERE email = :email";
    private final String SELECT_ALL_IDS = "SELECT id FROM users";
    private String insertUsers = "INSERT INTO users (first_name, last_name, email, screen_name, role) VALUES ";

    private NamedParameterJdbcTemplate template;

    public UserDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public User findById(Integer id) {
        System.out.println(SELECT_BY_ID);
        User user = template.queryForObject(SELECT_BY_ID, new MapSqlParameterSource("id", id), new UserRowMapper());

        return user;
    }

    @Override
    public void insertUsers(List<User> users, String type) {

        StringBuilder query = new StringBuilder(insertUsers);
        String firstName = "first_name?";
        String lastName = "last_name?";
        String email = "email?";
        String screenName = "screen_name?";
        String role = "role?";
        Map<String, Object> headers = new HashMap<String, Object>();
        SqlParameterSource param;

        for (int i = 0; i < users.size(); i++) {
            StringBuilder index = new StringBuilder().append(i);

            headers.put(firstName.replace("?", index.toString()), users.get(i).getFirstName());
            headers.put(lastName.replace("?", index.toString()), users.get(i).getLastName());
            headers.put(email.replace("?", index.toString()), users.get(i).getEmail());
            headers.put(screenName.replace("?", index.toString()), users.get(i).getEmail().split("@")[0]);
            headers.put(role.replace("?", index.toString()), type);

            query.append("(");
            query.append(":").append(firstName.replace("?", index.toString()));
            query.append(", ").append(":").append(lastName.replace("?", index.toString()));
            query.append(", ").append(":").append(email.replace("?", index.toString()));
            query.append(", ").append(":").append(screenName.replace("?", index.toString()));
            query.append(", ").append(":").append(role.replace("?", index.toString()));
            query.append(") ");
            query.append(i < (users.size() - 1) ? ", " : "");

        }

        param = new MapSqlParameterSource(headers);

        template.update(query.toString(), param);

    }

    @Override
    public List<String> getUserIds() {
        return template.query(SELECT_ALL_IDS, new IdRowMapper());
    }

    @Override
    public User findByEmail(String email) {
        try {
            return template.queryForObject(SELECT_BY_EMAIL, new MapSqlParameterSource("email", email),
                    new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
