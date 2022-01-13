package com.oswego.pcr.controllers;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oswego.pcr.services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    private final ObjectMapper mapper;

    @Autowired
    public AuthController(AuthService authService, ObjectMapper mapper) {
        this.authService = authService;
        this.mapper = mapper;
    }

    @PostMapping(path = "/token/validate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String validateToken(@RequestHeader Map<String, String> headers) throws JsonProcessingException {
        var user = authService.validateToken(headers.get("authorization"));
        var details = new Details(user.getFirstName() + " " + user.getLastName(), user.getEmail(), user.getPicture());
        return mapper.writeValueAsString(details);
    }

}

@Getter
@Setter
@AllArgsConstructor
class Details {
    String name;
    String email;
    String picture;
}
