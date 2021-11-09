package com.csc380.codepeerreview.repositories.implementation;

import com.csc380.codepeerreview.models.LikeInfo;
import com.csc380.codepeerreview.models.Post;
import com.csc380.codepeerreview.repositories.dao.PostDao;
import com.csc380.codepeerreview.repositories.mappers.PostRowMapper;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PostDaoImpl implements PostDao {

    private final String DELETE_POST = """
    DELETE FROM posts
    WHERE id = :id""";

    private final String SELECT_ALL_IDS = """
    SELECT id 
    FROM posts""";

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

    private final String SELECT_POSTS_LIKE = """
    SELECT posts.id, title, content, publish_date, code, posts.user_id, screen_name 
    FROM posts 
    INNER JOIN users ON posts.user_id = users.id 
    WHERE content LIKE :params""";

    private final String SELECT_LIKES = """
    SELECT user_id AS id, screen_name AS screenName 
    FROM post_likes 
    INNER JOIN users 
    ON users.id = post_likes.user_id 
    WHERE post_id = :post_id""";

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
    public List<String> getIds() {
        return template.query(SELECT_ALL_IDS, (rs, rowNum) -> String.valueOf(rs.getInt("id")));
    }

    @Override
    public Post findById(Integer id) {
        return template.queryForObject(SELECT_BY_ID, new MapSqlParameterSource("id", id), rowMapper);
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
    public void deletePost(Integer id) {
        params = new MapSqlParameterSource("id", id);
        template.update(DELETE_POST, params);
    }

    @Override
    public List<Post> searchWithParams(String searchParams) {
        params = new MapSqlParameterSource("params", "%" + searchParams + "%");
        return template.query(SELECT_POSTS_LIKE, params, rowMapper);
    }

    @Override
    public List<LikeInfo> getLikes(int id) {
        params = new MapSqlParameterSource("post_id", id);
        return template.query(SELECT_LIKES, params, BeanPropertyRowMapper.newInstance(LikeInfo.class));
    }
}
