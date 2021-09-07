package com.csc380.codepeerreview.responses;

public class SearchPostsResponse extends GetManyPostsResponse {

    public String query;

    public SearchPostsResponse(){
        super();
    }

    public void setQuery(String query){
        this.query = query;
    }

    public String getQuery(){
        return query;
    }
    
}
