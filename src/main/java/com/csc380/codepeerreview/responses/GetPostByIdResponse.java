package com.csc380.codepeerreview.responses;

import com.csc380.codepeerreview.models.Post;
import com.csc380.codepeerreview.models.Comment;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @NoArgsConstructor
public class GetPostByIdResponse extends BasicResponse {

    public Post post;
    public List<Comment> comments;
}
