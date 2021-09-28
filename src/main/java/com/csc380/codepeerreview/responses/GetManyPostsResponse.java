package com.csc380.codepeerreview.responses;

import java.util.List;

import com.csc380.codepeerreview.models.Post;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @NoArgsConstructor
public class GetManyPostsResponse extends BasicResponse {

    public List<Post> posts;

}
