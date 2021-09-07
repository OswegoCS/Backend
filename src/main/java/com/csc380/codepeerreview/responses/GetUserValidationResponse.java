package com.csc380.codepeerreview.responses;

public class GetUserValidationResponse extends BasicResponse {

    private boolean validity;

    public GetUserValidationResponse() {
    }

    public boolean getValidity() {
        return validity;
    }

    public void setValidity(boolean validity) {
        this.validity = validity;
    }

}
