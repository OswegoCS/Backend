package com.csc380.codepeerreview.controllers;

/*
 * import java.util.List;
 * 
 * import com.csc380.codepeerreview.models.User; import
 * com.csc380.codepeerreview.repositories.dao.UserRepository;
 * 
 * import org.bson.types.ObjectId; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.web.bind.annotation.CrossOrigin; import
 * org.springframework.web.bind.annotation.DeleteMapping; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.PutMapping; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.ResponseStatus; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * @RestController
 * 
 * @CrossOrigin
 * 
 * @RequestMapping("/api") public class StudentController {
 * 
 * @Autowired public UserRepository userRepository;
 * 
 * @GetMapping(value = "/students") public List<User> getAllStudents() {
 * 
 * return userRepository.findByType("student"); }
 * 
 * @PostMapping(value = "/students") public User createStudent(@RequestBody User
 * student) {
 * 
 * // make the username and set the type upon creation
 * student.setUsername(student.makeUsername(student.getEmail()));
 * student.setType("student"); User insertedStudent =
 * userRepository.insert(student);
 * 
 * return insertedStudent; }
 * 
 * // Delete all students
 * 
 * @DeleteMapping(value = "/students/deleteAllStudents") public String
 * deleteAllStudents() { userRepository.deleteByType("student"); return
 * "All students deleted"; }
 * 
 * // Delete particular Students
 * 
 * @DeleteMapping(value = "/students/{id}") public String
 * deleteStudent(@PathVariable String id) { User student =
 * userRepository.findById(new ObjectId(id)); if (student != null) {
 * userRepository.deleteById(new ObjectId(id)); return "Student deleted. _id = "
 * + id; } else return "Student not found. Please try again."; }
 * 
 * @ResponseStatus(value = HttpStatus.NOT_FOUND) public class
 * ResourceNotFoundException extends RuntimeException { /**
 *
 */
/*
 * private static final long serialVersionUID = 1L;
 * 
 * public ResourceNotFoundException() { } }
 * 
 * @PutMapping(value = "/students/{id}") public User editStudent(@PathVariable
 * ObjectId id, @RequestBody User student) { User editedStudent =
 * userRepository.findById(id);
 * 
 * editedStudent.setNameLast(student.getNameLast());
 * editedStudent.setNameFirst(student.getNameFirst());
 * editedStudent.setSchoolID(student.getSchoolID());
 * editedStudent.setEmail(student.getEmail());
 * editedStudent.setCourse(student.getCourse());
 * editedStudent.setType(student.getType());
 * editedStudent.makeUsername(student.getEmail());
 * 
 * userRepository.save(editedStudent);
 * 
 * return editedStudent;
 * 
 * }
 * 
 * }
 */