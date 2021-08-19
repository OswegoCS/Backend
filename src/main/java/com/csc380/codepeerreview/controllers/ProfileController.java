package com.csc380.codepeerreview.controllers;
/*
 * import com.csc380.codepeerreview.models.User; import
 * com.csc380.codepeerreview.repositories.dao.CommentsRepo; import
 * com.csc380.codepeerreview.repositories.dao.PostDao; import
 * com.csc380.codepeerreview.repositories.dao.UserRepository; import
 * com.google.gson.Gson; import com.google.gson.GsonBuilder;
 * 
 * import org.bson.types.ObjectId; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.web.bind.annotation.CrossOrigin; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * @RestController
 * 
 * @CrossOrigin
 * 
 * @RequestMapping("/api") public class ProfileController {
 * 
 * private Gson gson = new GsonBuilder().setPrettyPrinting().create();
 * 
 * @Autowired public UserRepository userRepo;
 * 
 * @Autowired public PostDao postRepo;
 * 
 * @Autowired public CommentsRepo commentRepo;
 * 
 * @GetMapping(path = "/users/id/{id}") public String
 * getUser(@PathVariable("id") String id) { String userJson; User user =
 * userRepo.findById(new ObjectId(id));
 * 
 * if (user != null) { userJson = gson.toJson(user); return userJson; } else
 * return "{\"success\": false}";
 * 
 * }
 * 
 * @GetMapping(path = "/users/id/{email}/profile") public String
 * getProfile(@PathVariable("email") String email) {
 * 
 * User user = userRepo.findByEmail(email);
 * 
 * if (user != null) {
 * 
 * String userJson = gson.toJson(user);
 * 
 * String json = "{\"first\": \"" + user.getNameFirst() + "\"," + "\"last\": \""
 * + user.getNameLast() + "\"," + "\"email\": \"" + user.getEmail() + "\"," +
 * "\"type\": \"" + user.getType() + "\"," + "\"course\": \"" + user.getCourse()
 * + "\"}"; return json; } else { return "{\"success\": false}"; } } }
 */