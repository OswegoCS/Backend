package com.oswego.pcr.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportedComment {

    private int commentId;
    private int reporterId;
    private String reason;
    private String dateReported;

}
