package com.oswego.pcr.repositories.implementation;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import com.oswego.pcr.models.Post;
import com.oswego.pcr.repositories.dao.PostDao;
import com.oswego.pcr.repositories.mappers.PostRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public class PostDaoImpl implements PostDao {

    private final String DELETE_POST = """
            DELETE FROM posts
            WHERE id = :id
            AND user_id = :userId
            """;

    private final String SELECT_ALL = """
            SELECT posts.id, title, content, publish_date, code, posts.user_id, screen_name
            FROM posts
            INNER JOIN users ON posts.user_id = users.id
            ORDER BY publish_date DESC""";

    private final String INSERT_POST = """
            INSERT INTO posts (title, content, code, user_id, publish_date)
            VALUES (:title, :content, :code, (SELECT id FROM users WHERE screen_name = :screen_name), :publish_date)
            RETURNING id""";

    private final String UPDATE_POST = """
            UPDATE posts
            SET title = :title, content = :content, code = :code
            WHERE id = :id""";

    private final String SELECT_BY_ID = """
            SELECT posts.id, title, content, publish_date, code, posts.user_id, screen_name
            FROM posts
            INNER JOIN users ON posts.user_id = users.id
            WHERE posts.id = :id""";

    private final String SELECT_BY_USER_ID = """
            SELECT posts.id, title, content, publish_date, code, posts.user_id, screen_name
            FROM posts
            INNER JOIN users ON posts.user_id = users.id
            WHERE users.id = :user_id
            ORDER BY publish_date DESC""";

    private final String DELETE_COMMENTS_AFTER_POST_DELETION = """
            DELETE FROM comments WHERE post_id = :id
            """;

    private final String DELETE_REPORTED_AFTER_POST_DELETION = """
            DELETE FROM reported_posts WHERE post_id = :id
            """;

    private final NamedParameterJdbcTemplate template;
    private final RowMapper<Post> rowMapper;
    private SqlParameterSource params;

    @Autowired
    public PostDaoImpl(NamedParameterJdbcTemplate template, PostRowMapper rowMapper) {
        this.template = template;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<Post> findAll() {
        return template.query(SELECT_ALL, rowMapper);
    }

    @Override
    public Post findById(Integer id) {
        try {
            return template.queryForObject(SELECT_BY_ID, new MapSqlParameterSource("id", id), rowMapper);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No posts with id: " + id);
        }
    }

    @Override
    public List<Post> findByUserId(Integer id) {
        return template.query(SELECT_BY_USER_ID, new MapSqlParameterSource("user_id", id), rowMapper);
    }

    @Override
    public int insertPost(Post post) {
        params = new MapSqlParameterSource()
                .addValue("screen_name", post.getScreenName())
                .addValue("title", post.getTitle())
                .addValue("content", post.getContent())
                .addValue("code", post.getCode())
                .addValue("publish_date", Timestamp.from(Instant.now()));
        return template.queryForObject(INSERT_POST, params, Integer.class);
    }

    @Override
    public void updatePost(Post post) {
        params = new MapSqlParameterSource()
                .addValue("title", post.getTitle())
                .addValue("content", post.getContent())
                .addValue("code", post.getCode())
                .addValue("id", post.getId());
        template.update(UPDATE_POST, params);
    }

    @Override
    public void deletePost(Integer id, Integer userId) {
        // Need to delete post and all of its comments
        params = new MapSqlParameterSource().addValue("id", id).addValue("userId", userId);
        var rowsAffected = template.update(DELETE_POST, params);
        if (rowsAffected == 0)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        template.update(DELETE_COMMENTS_AFTER_POST_DELETION, params);
        template.update(DELETE_REPORTED_AFTER_POST_DELETION, params);
    }

    @Override
    public void reportPost(Integer id, Integer userId, String reason) {
        String insertReportedPost = """
                INSERT INTO reported_posts (post_id, reason, reporter_id)
                VALUES ((SELECT id FROM posts WHERE id=:id), :reason, :user_id)
                """;
        params = new MapSqlParameterSource().addValue("id", id).addValue("userId", userId).addValue("reason", reason);
        try {
            template.update(insertReportedPost, params);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Post> findReportedPosts() {
        String insertReportedPost = """
                SELECT posts.id, title, content, publish_date, code, posts.user_id, screen_name
                FROM posts
                INNER JOIN users ON posts.user_id = users.id
                ORDER BY publish_date DESC""";

        try {
            return template.query(insertReportedPost, params, rowMapper);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
