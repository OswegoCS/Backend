package com.oswego.pcr.services;

import java.io.IOException;

import javax.annotation.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oswego.pcr.models.UserDetails;
import com.oswego.pcr.repositories.dao.UserDao;
import com.oswego.pcr.repositories.implementation.UserDaoImpl;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Service
public class AuthService {

    // private final String URL = "http://localhost:3000/api/validate";
    private final String URL = "http://moxie.cs.oswego.edu:80/api/validate";
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Resource
    private UserDao userRepo;
    private ObjectMapper objectMapper;

    @Autowired
    public AuthService(UserDaoImpl userRepo, ObjectMapper objectMapper) {
        this.userRepo = userRepo;
        this.objectMapper = objectMapper;
    }

    public UserDetails validateToken(String tokenStr) {
        log.info("Attempting to validate token");
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(URL);
            var token = new Token(tokenStr);
            var tokenJson = objectMapper.writeValueAsString(token);
            StringEntity postingString = new StringEntity(tokenJson);
            request.setEntity(postingString);
            request.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201)
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            String responseString = new BasicResponseHandler().handleResponse(response);
            var details = objectMapper.readValue(responseString, UserDetails.class);
            var user = userRepo.findByEmail(details.getEmail());
            details.setUser(user);
            return details;
        } catch (IOException e) {
            log.error("Error processing token");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public boolean isOwner(String username, UserDetails userDetails) {
        if (username.equals(userDetails.getEmail().split("@")[0]))
            return true;
        return false;
    }
}

@AllArgsConstructor
@Getter
@Setter
class Token {
    private String token;
}
