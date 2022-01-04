package com.oswego.pcr.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import javax.annotation.Resource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oswego.pcr.models.UserDetails;
import com.oswego.pcr.repositories.dao.UserDao;
import com.oswego.pcr.repositories.implementation.UserDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    // Reading authentication server url from environment variable
    // Fallback value is to try and use localhost
    @Value("${authURL:http://localhost:3000/api/validate}")
    private String URL;
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
        log.info("Attempting to validate token at " + URL);
        var values = new HashMap<String, String>();
        values.put("token", tokenStr);
        try {
            String requestBody = objectMapper
                    .writeValueAsString(values);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody)).header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200 && response.statusCode() != 201)
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            var details = objectMapper.readValue(response.body(), UserDetails.class);
            var user = userRepo.findByEmail(details.getEmail());
            details.setUser(user);
            return details;
        } catch (InterruptedException | IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public boolean isOwner(String username, UserDetails userDetails) {
        if (username.equals(userDetails.getEmail().split("@")[0]))
            return true;
        return false;
    }
}
