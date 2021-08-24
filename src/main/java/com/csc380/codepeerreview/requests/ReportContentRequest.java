package com.csc380.codepeerreview.requests;

public class ReportContentRequest extends BasicRequest {

    private int reporterId;
    private String reason;

    public ReportContentRequest() {
        super();
    }

    public int getReporterId() {
        return reporterId;
    }

    public void setReporterId(int reporterId) {
        this.reporterId = reporterId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
