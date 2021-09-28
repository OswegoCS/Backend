package com.csc380.codepeerreview.repositories.implementation;

import java.util.List;
import java.time.Instant;
import java.sql.Timestamp;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.csc380.codepeerreview.models.Comment;
import com.csc380.codepeerreview.models.ReportedComment;
import com.csc380.codepeerreview.repositories.dao.CommentDao;
import com.csc380.codepeerreview.repositories.mappers.CommentRowMapper;

@Repository
public class CommentDaoImpl implements CommentDao {

    final private String SELECT_BY_POST_ID = """
    SELECT post_id, user_id, comments.id, content, publish_date, users.screen_name 
    FROM comments 
    INNER JOIN users ON comments.user_id = users.id 
    WHERE post_id = :post_id""";

    final private String SELECT_REPORTED = """
    SELECT comments.user_id AS authorId, reporter_id AS reporterId, post_id AS postId, comments.id AS commentId, users.screen_name AS author, content, reason, publish_date AS \"date\", (Select screen_name as reporter from users where id = reported_comments.reporter_id) 
    FROM reported_comments 
    INNER JOIN comments ON comments.id = reported_comments.id 
    INNER JOIN users ON comments.user_id = users.id""";

    final private String INSERT_COMMENT = """
    INSERT INTO comments (post_id, content, user_id, publish_date) 
    VALUES (:post_id, :content, (SELECT id FROM users WHERE screen_name = :screen_name), :publish_date)""";

    final private String DELETE_COMMENT = """
    DELETE FROM
    comments WHERE id = :id""";

    final private String REPORT_COMMENT = """
    INSERT INTO reported_comments (id, reason, reporter_id) 
    VALUES (:id, :reason, :reporter_id)""";

    final private String LIKE_COMMENT = """
    INSERT INTO comment_likes (user_id, comment_id) 
    VALUES (:user_id,:comment_id)""";

    final private String GET_LIKES = """
    SELECT user_id,screen_name 
    FROM comment_likes 
    INNER JOIN users ON users.id = comment_likes.user_id 
    WHERE comment_id = :comment_id""";

    private NamedParameterJdbcTemplate template;

    public CommentDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Comment> findByPostId(Integer id) {
        List<Comment> comments = null;
        comments = template.query(
            SELECT_BY_POST_ID, new MapSqlParameterSource("post_id", id), new CommentRowMapper());
        return comments;
    }

    @Override
    public void insertComment(Comment comment) {

        SqlParameterSource param = new MapSqlParameterSource()
            .addValue("post_id", comment.getPostId())
            .addValue("publish_date", Timestamp.from(Instant.now()))
            .addValue("content", comment.getContent())
            .addValue("screen_name", comment.getScreenName());

        template.update(INSERT_COMMENT, param);

    }

    @Override
    public void deleteComment(Integer id) {

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

        template.update(DELETE_COMMENT, param);

    }

    @Override
    public void reportComment(Integer id, Integer reporterId, String reason) {

        SqlParameterSource param = new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("reporter_id", reporterId)
            .addValue("reason", reason);

        template.update(REPORT_COMMENT, param);
    }

    @Override
    public void likeComment(Integer commentId, Integer userId) {
        SqlParameterSource param = new MapSqlParameterSource()
        .addValue("comment_id", commentId)
        .addValue("user_id", userId);

        template.update(LIKE_COMMENT, param);
    }

    @Override
    public List<String> getLikes(Integer id) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("comment_id", id);

        return template.query(GET_LIKES, param, (rs, rn) -> String.valueOf(rs.getString("screen_name")));

    }

    @Override
    public List<ReportedComment> getReportedComments() {

        return template.query(SELECT_REPORTED, BeanPropertyRowMapper.newInstance(ReportedComment.class));
    }

}
