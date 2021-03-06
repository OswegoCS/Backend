package com.oswego.pcr.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.oswego.pcr.models.User;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int arg1) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setScreenName(rs.getString("screen_name"));
        user.setCourse(rs.getString("course"));
        return user;
    }

}
