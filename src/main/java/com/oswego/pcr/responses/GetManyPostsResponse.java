package com.oswego.pcr.responses;

import java.util.List;

import com.oswego.pcr.models.Post;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class GetManyPostsResponse extends BasicResponse {

    public List<Post> posts;

}
