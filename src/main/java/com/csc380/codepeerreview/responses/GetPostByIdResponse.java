package com.csc380.codepeerreview.responses;

import com.csc380.codepeerreview.models.Post;
import com.csc380.codepeerreview.models.Comment;
import java.util.List;

public class GetPostByIdResponse extends BasicResponse {

    public Post post;
    public List<Comment> comments;

    public GetPostByIdResponse() {
        super();
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
