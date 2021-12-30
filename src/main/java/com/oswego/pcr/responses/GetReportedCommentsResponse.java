package com.oswego.pcr.responses;

import java.util.List;

import com.oswego.pcr.models.ReportedComment;

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
