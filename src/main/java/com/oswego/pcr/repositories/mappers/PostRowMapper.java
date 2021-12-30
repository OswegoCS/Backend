package com.oswego.pcr.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import com.oswego.pcr.models.Post;

import java.time.LocalDateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class PostRowMapper implements RowMapper<Post> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE MMM dd 'at' h:mm a");

    @Override
    public Post mapRow(ResultSet rs, int arg1) throws SQLException {
        Post post = new Post();
        post.setId(rs.getInt("id"));
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));
        post.setCode(rs.getString("code"));
        post.setScreenName(rs.getString("screen_name"));
        post.setDate(formatter.format(rs.getObject("publish_date", LocalDateTime.class)));
        post.setUserId(rs.getInt("user_id"));
        return post;
    }

}
