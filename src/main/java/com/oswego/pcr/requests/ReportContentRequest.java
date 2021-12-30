package com.oswego.pcr.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportContentRequest extends BasicRequest {

    private int reporterId;
    private String reason;
}
