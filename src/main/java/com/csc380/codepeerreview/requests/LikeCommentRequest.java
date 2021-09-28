package com.csc380.codepeerreview.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class LikeCommentRequest extends BasicRequest {

    private int userId;

}
