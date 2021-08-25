package com.csc380.codepeerreview.responses;

import java.util.List;

import com.csc380.codepeerreview.models.ReportedComment;

public class GetReportedCommentsResponse extends BasicResponse {

    private List<ReportedComment> comments;

    public GetReportedCommentsResponse() {
        super();
    }

    public void setComments(List<ReportedComment> comments) {
        this.comments = comments;
    }

    public List<ReportedComment> getComments() {
        return comments;
    }

}
