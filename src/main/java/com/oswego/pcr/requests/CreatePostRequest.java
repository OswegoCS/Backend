package com.oswego.pcr.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePostRequest extends BasicRequest {

    private String title;
    private String content;
    private String date;
    private String code;
    private String file;

}
