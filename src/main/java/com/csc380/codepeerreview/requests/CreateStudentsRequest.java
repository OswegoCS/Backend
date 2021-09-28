package com.csc380.codepeerreview.requests;

import java.util.List;
import com.csc380.codepeerreview.models.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CreateStudentsRequest extends BasicRequest {

    private List<User> users;

}
