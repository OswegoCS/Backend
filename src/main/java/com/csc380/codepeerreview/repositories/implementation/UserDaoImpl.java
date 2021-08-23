package com.csc380.codepeerreview.repositories.implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csc380.codepeerreview.models.User;
import com.csc380.codepeerreview.repositories.dao.UserDao;
import com.csc380.codepeerreview.repositories.mappers.UserRowMapper;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.lang.Nullable;

@Repository
public class UserDaoImpl implements UserDao {

    private final String SELECT_BY_ID = "SELECT screen_name, first_name, last_name, email FROM users WHERE id = :id";
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
    public void insertUsers(List<User> students) {

        StringBuilder query = new StringBuilder(insertUsers);
        String firstName = "first_name?";
        String lastName = "last_name?";
        String email = "email?";
        String screenName = "screen_name?";
        String role = "role?";
        Map<String, Object> headers = new HashMap<String, Object>();
        SqlParameterSource param;

        for (int i = 0; i < students.size(); i++) {
            StringBuilder index = new StringBuilder().append(i);

            headers.put(firstName.replace("?", index.toString()), students.get(i).getFirstName());
            headers.put(lastName.replace("?", index.toString()), students.get(i).getLastName());
            headers.put(email.replace("?", index.toString()), students.get(i).getEmail());
            headers.put(screenName.replace("?", index.toString()), students.get(i).getEmail().split("@")[0]);
            headers.put(role.replace("?", index.toString()), "student");

            query.append("(");
            query.append(":").append(firstName.replace("?", index.toString()));
            query.append(", ").append(":").append(lastName.replace("?", index.toString()));
            query.append(", ").append(":").append(email.replace("?", index.toString()));
            query.append(", ").append(":").append(screenName.replace("?", index.toString()));
            query.append(", ").append(":").append(role.replace("?", index.toString()));
            query.append(") ");
            query.append(i < (students.size() - 1) ? ", " : "");

        }

        param = new MapSqlParameterSource(headers);

        template.update(query.toString(), param);

    }

}
