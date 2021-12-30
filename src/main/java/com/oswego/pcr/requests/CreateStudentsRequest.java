package com.oswego.pcr.requests;

import java.util.List;

import com.oswego.pcr.models.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateStudentsRequest extends BasicRequest {

    private List<User> users;

}
