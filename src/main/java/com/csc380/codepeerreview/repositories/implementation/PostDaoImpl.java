package com.csc380.codepeerreview.repositories.implementation;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.csc380.codepeerreview.models.Post;
import com.csc380.codepeerreview.repositories.dao.PostDao;
import com.csc380.codepeerreview.repositories.mappers.PostRowMapper;
import com.csc380.codepeerreview.repositories.mappers.IdRowMapper;

@Repository
public class PostDaoImpl implements PostDao {

    private final String SELECT_ALL = "SELECT posts.id, title, content, publish_date, code, posts.user_id, screen_name FROM posts INNER JOIN users ON posts.user_id = users.id";

    private final String SELECT_ALL_IDS = "SELECT id FROM posts";

    private final String SELECT_BY_ID = "SELECT posts.id, title, content, publish_date, code, posts.user_id, screen_name FROM posts INNER JOIN users ON posts.user_id = users.id WHERE posts.id = :post_id";

    private final String SELECT_BY_USER_ID = "SELECT posts.id, title, content, publish_date, code, posts.user_id, screen_name FROM posts INNER JOIN users ON posts.user_id = users.id WHERE users.id = :user_id";

    private final String INSERT_POST = "INSERT INTO posts (title, content, publish_date, code, user_id) VALUES (:title, :content, :publish_date, :code, (SELECT id FROM users WHERE screen_name = :screen_name)) RETURNING id";

    private final String UPDATE_POST = "UPDATE posts SET title = :title, content = :content, code = :code, publish_date = :publish_date WHERE id = :id";

    private final String DELETE_POST = "DELETE FROM posts WHERE id = :id";

    private NamedParameterJdbcTemplate template;

    public PostDaoImpl(NamedParameterJdbcTemplate template) {
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
    public List<Post> findByUserId(Integer id) {
        List<Post> posts = null;
        posts = template.query(SELECT_BY_USER_ID, new MapSqlParameterSource("user_id", id), new PostRowMapper());
        return posts;
    }

    @Override
    public int insertPost(Post post) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("screen_name", post.getScreenName())
                .addValue("title", post.getTitle()).addValue("content", post.getTitle())
                .addValue("publish_date", post.getDate()).addValue("code", post.getCode());

        Integer id = template.queryForObject(INSERT_POST, param, Integer.class);

        return id;
    }

    @Override
    public void updatePost(Post post) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("title", post.getTitle())
                .addValue("content", post.getTitle()).addValue("publish_date", post.getDate())
                .addValue("code", post.getCode()).addValue("id", post.getId());

        template.update(UPDATE_POST, param);

    }

    @Override
    public void deletePost(Integer id) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        template.update(DELETE_POST, param);
    }

}
