package com.oswego.pcr.responses;

import java.util.List;

import com.oswego.pcr.models.Comment;
import com.oswego.pcr.models.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetPostByIdResponse extends BasicResponse {

    public Post post;
    public List<Comment> comments;
    public boolean owner;
}
