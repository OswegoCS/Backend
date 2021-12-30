package com.oswego.pcr.models;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Likes {

    private int count;
    private List<String> users;

    public Likes(List<String> users) {
        this.users = users;
        this.count = users.size();
    }
}
