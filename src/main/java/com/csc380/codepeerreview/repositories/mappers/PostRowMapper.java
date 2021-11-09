package com.csc380.codepeerreview.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.csc380.codepeerreview.models.Post;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class PostRowMapper implements RowMapper<Post> {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE MMM dd 'at' h:mm a");

    @Override
    public Post mapRow(ResultSet rs, int arg1) throws SQLException {
        Post post = new Post();
        post.setId(rs.getInt("id"));
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));
        post.setCode(rs.getString("code"));
        post.setScreenName(rs.getString("screen_name"));
        post.setDate(formatter.format(rs.getObject("publish_date", LocalDateTime.class)));

        return post;
    }

}
