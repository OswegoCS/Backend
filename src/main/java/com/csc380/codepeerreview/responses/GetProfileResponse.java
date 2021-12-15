package com.csc380.codepeerreview.responses;

import java.util.List;

import com.csc380.codepeerreview.models.Post;
import com.csc380.codepeerreview.models.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor

public class GetProfileResponse extends BasicResponse {

    private User user;
    private List<Post> posts;
    private List<User> users;
}
