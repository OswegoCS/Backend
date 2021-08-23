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
    final private String INSERT_COMMENT = "INSERT INTO comments (post_id, content, publish_date, user_id) VALUES (:postid, :content, :publish_date, (SELECT id FROM users WHERE screen_name = :screen_name))";
    final private String DELETE_COMMENT = "DELETE FROM posts WHERE id = :id";

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

    @Override
    public void insertComment(Comment comment) {

        SqlParameterSource param = new MapSqlParameterSource().addValue("post_id", comment.getPostId())
                .addValue("content", comment.getContent()).addValue("publish_date", comment.getDate())
                .addValue("screen_name", comment.getScreenName());

        template.update(INSERT_COMMENT, param);

    }

    @Override
    public void deleteComment(Integer id) {

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

        template.update(DELETE_COMMENT, param);

    }

}
