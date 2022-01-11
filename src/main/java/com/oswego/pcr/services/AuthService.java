package com.oswego.pcr.services;

import java.util.Collections;

import javax.annotation.Resource;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.oswego.pcr.models.User;
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

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Value("${GOOGLE_CLIENT_ID}")
    private String CLIENT_ID;

    @Resource
    private UserDao userRepo;

    @Autowired
    public AuthService(UserDaoImpl userRepo) {
        this.userRepo = userRepo;
    }

    public User validateToken(String tokenStr) {
        log.info("Attempting to validate token");
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
                    new GsonFactory())
                            .setAudience(Collections.singletonList(CLIENT_ID))
                            .build();
            GoogleIdToken idToken = verifier.verify(tokenStr);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                String email = payload.getEmail();
                return userRepo.findByEmail(email);
            }
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public boolean isOwner(String username, User user) {
        if (username.equals(user.getEmail().split("@")[0]))
            return true;
        return false;
    }
}
