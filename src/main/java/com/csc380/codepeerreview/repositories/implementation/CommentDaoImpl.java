package com.csc380.codepeerreview.repositories.implementation;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.csc380.codepeerreview.models.Comment;
import com.csc380.codepeerreview.repositories.dao.CommentDao;
import com.csc380.codepeerreview.repositories.mappers.CommentRowMapper;

@Repository
public class CommentDaoImpl implements CommentDao {

    // final private String SELECT_BY_POST_ID = "SELECT id, post_id, content,
    // publish_date, FROM comments WHERE post_id = :post_id";
    final private String SELECT_BY_POST_ID = "SELECT comments.id, post_id, content, publish_date, users.screen_name FROM comments INNER JOIN users ON comments.user_id=users.id WHERE comments.post_id=:post_id";

    // final private String SELECT_BY_USER_ID = "SELECT id, post_id, content,
    // publish_date FROM comments WHERE post_id = :post_id";
    final private String SELECT_BY_REPORTED = "SELECT * FROM reported_comments WHERE reported = TRUE";

    private NamedParameterJdbcTemplate template;

    public CommentDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Comment> findByPostId(Integer id) {
        List<Comment> comments = null;
        // SqlParameterSource param = new MapSqlParameterSource("post_id", id);

        // comments = template.query(SELECT_BY_POST_ID, param,
        // BeanPropertyRowMapper.newInstance(Comment.class));
        comments = template.query(SELECT_BY_POST_ID, new MapSqlParameterSource("post_id", id), new CommentRowMapper());
        return comments;
    }

    @Override
    public List<Comment> findByReported() {

        return template.query(SELECT_BY_REPORTED, BeanPropertyRowMapper.newInstance(Comment.class));

    }

}
