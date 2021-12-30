package com.oswego.pcr.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class BasicRequest {

    private int id;
    private String screenName;

}
