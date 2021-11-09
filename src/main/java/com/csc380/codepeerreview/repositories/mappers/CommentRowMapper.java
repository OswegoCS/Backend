package com.csc380.codepeerreview.repositories.mappers;

import com.csc380.codepeerreview.models.Comment;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CommentRowMapper implements RowMapper<Comment> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy 'at' h:mm:ss a	");

    @Override
    public Comment mapRow(ResultSet rs, int arg1) throws SQLException {
        Comment comment = new Comment();
        comment.setId(rs.getInt("id"));
        comment.setPostId(rs.getInt("post_id"));
        comment.setUserId(rs.getInt("user_id"));
        comment.setContent(rs.getString("content"));
        comment.setDate(formatter.format(rs.getObject("publish_date", LocalDateTime.class)));
        comment.setScreenName(rs.getString("screen_name"));
        return comment;
    }

}
