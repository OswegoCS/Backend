package com.csc380.codepeerreview.repositories.implementation;

import com.csc380.codepeerreview.models.Comment;
import com.csc380.codepeerreview.models.Likes;
import com.csc380.codepeerreview.models.ReportedComment;
import com.csc380.codepeerreview.repositories.dao.CommentDao;
import com.csc380.codepeerreview.repositories.mappers.CommentRowMapper;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

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
    VALUES (:post_id, :content, (SELECT id FROM users WHERE screen_name = :screen_name), :publish_date) 
    RETURNING id, post_id, user_id, content, publish_date, (SELECT screen_name FROM users WHERE id=user_id)""";

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

    private final NamedParameterJdbcTemplate template;
    private final RowMapper<Comment> commentMapper;
    private SqlParameterSource params;

    @Autowired
    public CommentDaoImpl(NamedParameterJdbcTemplate template, CommentRowMapper commentMapper) {
        this.template = template;
        this.commentMapper = commentMapper;
    }

    @Override
    public List<Comment> findByPostId(Integer id) {
        try {
        List<Comment> comments = null;
        comments = template.query(
            SELECT_BY_POST_ID, new MapSqlParameterSource("post_id", id), commentMapper);
        comments.forEach(comment -> {
                var usersWhoLiked = getLikes(comment.getId());
                comment.setLikes(new Likes(usersWhoLiked));
        });
        return comments;
    } catch(EmptyResultDataAccessException e) {
        return null;
    } catch(Exception e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @Override
    public Comment insertComment(Comment newComment) {
        params = new MapSqlParameterSource()
            .addValue("post_id", newComment.getPostId())
            .addValue("publish_date", Timestamp.from(Instant.now()))
            .addValue("content", newComment.getContent())
            .addValue("screen_name", newComment.getScreenName());
        var comment = template.queryForObject(INSERT_COMMENT, params, commentMapper);
        var usersWhoLiked = getLikes(comment.getId());
        comment.setLikes(new Likes(usersWhoLiked));
        return comment;
    }

    @Override
    public void deleteComment(Integer id) {
        params = new MapSqlParameterSource("id", id);
        template.update(DELETE_COMMENT, params);
    }

    @Override
    public void reportComment(ReportedComment comment) {
        params = new MapSqlParameterSource()
            .addValue("id", comment.getCommentId())
            .addValue("reporter_id", comment.getReporterId())
            .addValue("reason", comment.getReason());
        template.update(REPORT_COMMENT, params);
    }

    @Override
    public void likeComment(Integer commentId, Integer userId) {
        params = new MapSqlParameterSource()
            .addValue("comment_id", commentId)
            .addValue("user_id", userId);
        template.update(LIKE_COMMENT, params);
    }

    @Override
    public List<String> getLikes(Integer id) {
        params = new MapSqlParameterSource("comment_id", id);
        return template.query(GET_LIKES, params, (rs, rn) -> String.valueOf(rs.getString("screen_name")));
    }

    @Override
    public List<ReportedComment> getReportedComments() {
        return template.query(SELECT_REPORTED, BeanPropertyRowMapper.newInstance(ReportedComment.class));
    }

}
