package com.csc380.codepeerreview.responses;

import java.util.List;

public class GetIdsResponse extends BasicResponse {

    public List<String> ids;

    public GetIdsResponse() {
        super();
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

}
