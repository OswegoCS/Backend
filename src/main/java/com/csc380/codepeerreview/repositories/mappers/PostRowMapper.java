package com.csc380.codepeerreview.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.csc380.codepeerreview.models.Post;
import org.springframework.jdbc.core.RowMapper;

public class PostRowMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet rs, int arg1) throws SQLException {
        Post post = new Post();
        post.setId(rs.getInt("id"));
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));
        post.setCode(rs.getString("code"));
        post.setScreenName(rs.getString("screen_name"));
        post.setDate(rs.getString("publish_date"));

        return post;
    }

}
