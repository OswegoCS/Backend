package com.csc380.codepeerreview.repositories.implementation;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.csc380.codepeerreview.models.Post;
import com.csc380.codepeerreview.repositories.dao.PostDao;
import com.csc380.codepeerreview.repositories.mappers.PostRowMapper;
import com.csc380.codepeerreview.repositories.mappers.IdRowMapper;

@Repository
public class PostDaoImplementation implements PostDao {

    private final String SELECT_ALL = "SELECT posts.id, title, content, publish_date, code, posts.user_id, screen_name FROM posts INNER JOIN users ON posts.user_id = users.id";
    private final String SELECT_ALL_IDS = "SELECT id FROM posts";
    private final String SELECT_BY_ID = "SELECT posts.id, title, content, publish_date, code, posts.user_id, screen_name FROM posts INNER JOIN users ON posts.user_id = users.id WHERE posts.id = :post_id";
    private final String INSERT_POST = "INSERT INTO posts (title, content, publish_date, code, user_id) VALUES (:title, :content, :publish_date, :code, (SELECT id FROM users WHERE screen_name = :screen_name)) RETURNING id";

    NamedParameterJdbcTemplate template;

    public PostDaoImplementation(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Post> findAll() {
        return template.query(SELECT_ALL, new PostRowMapper());
    }

    @Override
    public List<String> getIds() {
        return template.query(SELECT_ALL_IDS, new IdRowMapper());
    }

    @Override
    public Post findById(Integer id) {
        Post post = null;
        post = template.queryForObject(SELECT_BY_ID, new MapSqlParameterSource("post_id", id), new PostRowMapper());
        return post;
    }

    @Override
    public int insertPost(Post post) {

        System.out.println(INSERT_POST);
        SqlParameterSource param = new MapSqlParameterSource().addValue("screen_name", post.getScreenName())
                .addValue("title", post.getTitle()).addValue("content", post.getTitle())
                .addValue("publish_date", post.getDate()).addValue("code", post.getCode());

        Integer id = template.queryForObject(INSERT_POST, param, Integer.class);

        return id;
    }

    /*
     * public List<Post> findReportedPosts() {
     * 
     * 
     * }
     * 
     * 
     * 
     * }
     */

}
