package com.oswego.pcr.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDetails {
    private String name;
    private String email;
    private String picture;
    private User user;
}
