package com.csc380.codepeerreview.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.csc380.codepeerreview.models.User;
import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int arg1) throws SQLException {
        System.out.println(rs.getString("first_name"));
        User user = new User();
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setScreenName(rs.getString("screen_name"));

        return user;
    }

}
