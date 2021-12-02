package com.csc380.codepeerreview.util;

import com.csc380.codepeerreview.models.User;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

public class FileHelper {
    
    public static List<String[]> parseCSV(File csv) {
        try {
            CSVReader reader = new CSVReader(new FileReader(csv));
            List<String[]> myEntries = reader.readAll();
            return myEntries;
        } catch ( IOException | CsvException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /*****************
     * Format of the csv is id, lastName, firstName, middleName, section, major, course, email, classStanding, address
     * @param csv: File
     * @return List<User>
     */
    public static List<User> convertCSVtoUsers(File csv) {
        List<String[]> csvContents = parseCSV(csv);
        List<User> users = new ArrayList<>();
        for(String[] row : csvContents){
            String lastName = row[1];
            String firstName = row[2];
            String course = row[6];
            String email = row[7];
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setCourse(course);
            user.setEmail(email);
            user.setScreenName(email.split("@")[0]);
            users.add(user);
        }
        return users;
    }

    public static void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot process empty file");
        }
    }

    public static void isCSV(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (!fileName.substring(fileName.lastIndexOf(".") + 1).equalsIgnoreCase("csv")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can only process CSV files");
        }
    }
}
