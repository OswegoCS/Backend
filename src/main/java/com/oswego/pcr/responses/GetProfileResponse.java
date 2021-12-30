package com.oswego.pcr.responses;

import java.util.List;

import com.oswego.pcr.models.Post;
import com.oswego.pcr.models.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class GetProfileResponse extends BasicResponse {

    private User user;
    private List<Post> posts;
    private List<User> students;
    private User instructor;
}
