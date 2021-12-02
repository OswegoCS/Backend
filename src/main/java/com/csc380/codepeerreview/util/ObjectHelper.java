package com.csc380.codepeerreview.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectHelper {

    public static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(ObjectHelper.class);
    
    public static <T> T convertStringToObject(String json, Class<T> objectClass){
        try {
            return mapper.readValue(json, objectClass);
        } catch(Exception e) {
            logger.error("Error parsing JSON", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
