package com.oswego.pcr.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
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
    @Value("${GOOGLE_CLIENT_ID:}")
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
            if (tokenStr == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            // Token looks like this: "Bearer ey.....gxp", need to split on space
            String token = tokenStr.split(" ")[1];
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
                    new GsonFactory())
                            .setAudience(Collections.singletonList(CLIENT_ID))
                            .build();
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken == null)
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String picture = (String) payload.get("picture");
            var user = userRepo.findByEmail(email);
            user.setPicture(picture);
            return user;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (GeneralSecurityException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public boolean isOwner(String username, User user) {
        if (username.equals(user.getEmail().split("@")[0]))
            return true;
        return false;
    }
}
