package com.oswego.pcr.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCommentRequest extends BasicRequest {

    private String content;
    private int postId;

}
