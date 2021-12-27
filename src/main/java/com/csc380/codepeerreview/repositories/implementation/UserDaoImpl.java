package com.csc380.codepeerreview.repositories.implementation;

import com.csc380.codepeerreview.models.Role;
import com.csc380.codepeerreview.models.User;
import com.csc380.codepeerreview.repositories.dao.UserDao;
import com.csc380.codepeerreview.repositories.mappers.UserRowMapper;

import java.util.ArrayList;
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

    private final String SELECT_INSTRUCTOR_BY_COURSE_ID = """
    SELECT id, screen_name, first_name, last_name, email, course 
    FROM users
    WHERE id = (SELECT instructor_id FROM courses WHERE id = :id)""";

    private final String SELECT_BY_EMAIL = """
    SELECT id, screen_name, first_name, last_name, email, course
    FROM users 
    WHERE email = :email""";

    private final String SELECT_BY_COURSE = """
    SELECT id, screen_name, first_name, last_name, email, course
    FROM users 
    WHERE course = :course""";

    private final String SELECT_ROLES = """
    SELECT user_roles.role_id AS roleId, roles.role_name AS roleName
    FROM roles
    INNER JOIN user_roles
	ON user_roles.role_id = roles.id
    WHERE user_roles.user_id = :id
    """;

    private final String insertStudentRoles = """
    INSERT INTO user_roles (user_id, role_id) 
    VALUES 
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
    "INSERT INTO users (first_name, last_name, email, screen_name, course) VALUES ";

    private final NamedParameterJdbcTemplate template;
    private final RowMapper<User> userMapper;

    @Autowired
    public UserDaoImpl(NamedParameterJdbcTemplate template, UserRowMapper userMapper) {
        this.template = template;
        this.userMapper = userMapper;
    }

    @Override
    public void insertUsers(List<User> users, String type) {
        StringBuilder query = new StringBuilder(insertUsers);
        String firstName = "first_name";
        String lastName = "last_name";
        String email = "email";
        String screenName = "screen_name";
        String courseName = "course";
        StringBuilder rolesQuery = new StringBuilder(insertStudentRoles);
        Map<String, Object> headers = new HashMap<String, Object>();
        SqlParameterSource param;

        for (int i = 0; i < users.size(); i++) {
            String index = String.valueOf(i);
            headers.put(firstName.concat(index), users.get(i).getFirstName());
            headers.put(lastName.concat(index), users.get(i).getLastName());
            headers.put(email.concat(index), users.get(i).getEmail());
            headers.put(screenName.concat(index), users.get(i).getEmail().split("@")[0]);
            headers.put(courseName.concat(index), users.get(i).getCourse());

            query.append("(")
            .append(":").append(firstName.concat(index))
            .append(", :").append(lastName.concat(index))
            .append(", :").append(email.concat(index))
            .append(", :").append(screenName.concat(index))
            .append(", :").append(courseName.concat(index))
            .append(") ")
            .append(i < (users.size() - 1) ? ", " : "");
        }
        query.append(" ON CONFLICT (email) DO NOTHING RETURNING id");
        param = new MapSqlParameterSource(headers);
        try{
            List<Integer> ids = template.query(query.toString(), param, (rs, rowNum) -> (rs.getInt("id")));
            headers.clear();
            for(int i = 0; i < ids.size(); i++) {
                int id = ids.get(i);
                headers.put("userId".concat(String.valueOf(i)), id);
                rolesQuery.append("(" + ":userId".concat(String.valueOf(i)) + ", 3)");
                rolesQuery.append(i < (ids.size() - 1) ? ", " : "");
            }
            template.update(rolesQuery.toString(), headers);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
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

    @Override
    public List<User> findByCourse(String course) {
        try{
            List<User> students = template.query(SELECT_BY_COURSE, new MapSqlParameterSource("course", course), userMapper);
            return students;
        } catch(EmptyResultDataAccessException e){
            //Not getting any results back is fine, just return an empty list
            return new ArrayList<User>();
        }catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public User findCourseInstructor(String course) {
        try {
            User instructor = template.queryForObject(SELECT_INSTRUCTOR_BY_COURSE_ID, new MapSqlParameterSource("id", course), BeanPropertyRowMapper.newInstance(User.class));
            return instructor;
        } catch(Exception e) {
            //Throw an exception, if we are not getting an instructor back, there are problems
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
