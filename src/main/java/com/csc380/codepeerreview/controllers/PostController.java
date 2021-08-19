package com.csc380.codepeerreview.controllers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import com.csc380.codepeerreview.models.Comment;
import com.csc380.codepeerreview.models.Post;
import com.csc380.codepeerreview.repositories.dao.CommentDao;
import com.csc380.codepeerreview.repositories.dao.PostDao;
//import com.csc380.codepeerreview.repositories.dao.UserRepository;
import com.csc380.codepeerreview.requests.CreatePostRequest;
import com.csc380.codepeerreview.responses.CreatePostResponse;
import com.csc380.codepeerreview.responses.GetIdsResponse;
import com.csc380.codepeerreview.responses.GetManyPostsResponse;
import com.csc380.codepeerreview.responses.GetPostByIdResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PostController {
    @Resource
    public PostDao postRepo;
    @Resource
    public CommentDao commentRepo;

    @GetMapping(value = "/posts")
    public GetManyPostsResponse getAllPosts() {

        List<Post> posts = null;
        GetManyPostsResponse response = new GetManyPostsResponse();

        try {
            posts = postRepo.findAll();
            Collections.reverse(posts);
            response.setMessage("success");
            response.setStatusCode(200);

        } catch (Exception e) {
            response.setMessage("failure caused by " + e.getMessage());
            response.setStatusCode(500);
        }
        response.setPosts(posts);

        return response;
    }
    /*
     * @GetMapping(path = "/posts/flagged/{flag}") public List<Post>
     * getPostsByFlagged(@PathVariable("flag") String boolStr) {
     * 
     * List<Post> posts; if (boolStr.equalsIgnoreCase("false")) { posts =
     * postRepo.findByFlagged(false); Collections.reverse(posts); return posts;
     * 
     * } else { posts = postRepo.findByFlagged(true); Collections.reverse(posts);
     * return posts; }
     * 
     * }
     */

    // Returns a Post with the given id
    @GetMapping(path = "/posts/id/{id}")
    public GetPostByIdResponse getPostsById(@PathVariable("id") Integer id) {
        GetPostByIdResponse response = new GetPostByIdResponse();
        List<Comment> comments = null;

        Post post = postRepo.findById(id);
        if (post == null) {
            response.setMessage("No posts with id " + id);
            response.setStatusCode(500);
        }
        comments = commentRepo.findByPostId(id);
        response.setPost(post);
        response.setComments(comments);
        return response;
    }

    // Returns a list of all post ids
    @GetMapping(path = "/posts/id")
    public GetIdsResponse getIds() {
        GetIdsResponse response = new GetIdsResponse();
        List<String> ids = postRepo.getIds();
        response.setIds(ids);
        return response;
    }

    @PostMapping("/posts/create")
    public CreatePostResponse createPost(@RequestBody CreatePostRequest request) throws Exception {
        String title = request.getTitle();
        String content = request.getContent();
        String code = request.getCode();
        String screenName = request.getScreenName();
        String file = request.getFile();
        String date = LocalDateTime.now().toString().substring(0, 10);
        // TO-DO: Validation of inputs. Language filter
        if (file != null) {
            code = file;
        }
        // Set the code
        if (code.equals("") || code == null) {
            code = "No code was provided for this post";
        }

        code = code.replaceAll("(\\\\r\\\\n|\\\\n)", "\n").replaceAll("(\\\\\")", "\"").replaceAll("(\\\\\\\\)",
                "\\\\");

        Post post = new Post();

        post.setTitle(title);
        post.setScreenName(screenName);
        post.setContent(content);
        post.setCode(code);
        post.setDate(date);

        int id = postRepo.insertPost(post);

        CreatePostResponse response = new CreatePostResponse();
        response.setId(id);

        return response;
    }

    /*
     * @PutMapping("/posts/report/{id}") public Post reportPost(@PathVariable("id")
     * Integer id) {
     * 
     * Post reportedPost = postRepo.findById(id);
     * 
     * reportedPost.setReported(true);
     * 
     * postRepo.save(reportedPost);
     * 
     * return reportedPost; }
     */

    /*
     * @PutMapping("/posts/restore/{id}") public Post
     * restorePost(@PathVariable("id") String id) {
     * 
     * Post reportedPost = postRepo.findById(new ObjectId(id));
     * 
     * reportedPost.setReported(false);
     * 
     * postRepo.save(reportedPost);
     * 
     * return reportedPost; }
     * 
     * 
     * @DeleteMapping("posts/id/{id}") public Post deletePost(@PathVariable("id")
     * String id) {
     * 
     * Post deletePost = postRepo.deleteById(new ObjectId(id)); return deletePost;
     * 
     * }
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public ResourceNotFoundException() {

        }
    }
    /*
     * @PutMapping(value = "/posts/{id}") public Post editPost(@PathVariable Integer
     * id, @RequestBody Post post) { Post editedPost = postRepo.findById(id);
     * 
     * editedPost.setUser(post.getUser()); editedPost.setTitle(post.getTitle());
     * editedPost.setContent(post.getContent()); editedPost.setDate(post.getDate());
     * 
     * return editedPost; }
     */
}
