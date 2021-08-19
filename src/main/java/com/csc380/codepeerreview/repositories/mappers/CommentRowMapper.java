package com.csc380.codepeerreview.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.csc380.codepeerreview.models.Comment;
import org.springframework.jdbc.core.RowMapper;

public class CommentRowMapper implements RowMapper<Comment> {

    @Override
    public Comment mapRow(ResultSet rs, int arg1) throws SQLException {
        Comment comment = new Comment();
        comment.setId(rs.getInt("id"));
        comment.setPostId(rs.getInt("post_id"));
        comment.setContent(rs.getString("content"));
        comment.setDate(rs.getString("publish_date"));

        return comment;
    }

}
