package com.csc380.codepeerreview.repositories.implementation;

import com.csc380.codepeerreview.models.Role;
import com.csc380.codepeerreview.models.User;
import com.csc380.codepeerreview.repositories.dao.UserDao;
import com.csc380.codepeerreview.repositories.mappers.UserRowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

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

    private final String SELECT_ROLES = """
    SELECT user_roles.role_id AS roleId, roles.role_name AS roleName
    FROM roles
    INNER JOIN user_roles
	ON user_roles.role_id = roles.id
    WHERE user_roles.user_id = :id
    """;

    /*Full query looks like this
    INSERT INTO users (first_name, last_name, email, screen_name) 
    VALUES 
    (:first_name0, :last_name0, :email0, :screen_name0),
    (:first_name1, :last_name1, :email1, :screen_name1),
    ...
    ...
    ON CONFLICT (email) DO NOTHING"
    */
    private String insertUsers = 
    "INSERT INTO users (first_name, last_name, email, screen_name) VALUES ";

    private final NamedParameterJdbcTemplate template;
    private final RowMapper<User> userMapper;

    @Autowired
    public UserDaoImpl(NamedParameterJdbcTemplate template, UserRowMapper userMapper) {
        this.template = template;
        this.userMapper = userMapper;
    }

    @Override
    public User findById(Integer id) {
        try {
        User user =  template.queryForObject(
            SELECT_BY_ID, new MapSqlParameterSource("id", id), userMapper);
            List<Role> roles = template.query(SELECT_ROLES, new MapSqlParameterSource("id", user.getId()), BeanPropertyRowMapper.newInstance(Role.class));
            user.setRoles(roles);
        return user;
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No users with id: " + id);
        }
        
    }

    @Override
    public void insertUsers(List<User> users, String type) {

        StringBuilder query = new StringBuilder(insertUsers);
        String firstName = "first_name";
        String lastName = "last_name";
        String email = "email";
        String screenName = "screen_name";
        Map<String, Object> headers = new HashMap<String, Object>();
        SqlParameterSource param;

        for (int i = 0; i < users.size(); i++) {
            String index = String.valueOf(i);
            headers.put(firstName.concat(index), users.get(i).getFirstName());
            headers.put(lastName.concat(index), users.get(i).getLastName());
            headers.put(email.concat(index), users.get(i).getEmail());
            headers.put(screenName.concat(index), users.get(i).getEmail().split("@")[0]);

            query.append("(")
            .append(":").append(firstName.concat(index))
            .append(", ").append(":").append(lastName.concat(index))
            .append(", ").append(":").append(email.concat(index))
            .append(", ").append(":").append(screenName.concat(index))
            .append(") ")
            .append(i < (users.size() - 1) ? ", " : "");
        }
        query.append(" ON CONFLICT (email) DO NOTHING");
        param = new MapSqlParameterSource(headers);
        try{
            template.update(query.toString(), param);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            User user =  template.queryForObject(
                SELECT_BY_EMAIL, new MapSqlParameterSource("email", email), userMapper);
            List<Role> roles = template.query(SELECT_ROLES, new MapSqlParameterSource("id", user.getId()), BeanPropertyRowMapper.newInstance(Role.class));
            user.setRoles(roles);
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No users with email: " + email);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
