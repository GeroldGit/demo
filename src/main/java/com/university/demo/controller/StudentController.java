package com.university.demo.controller;

import com.university.demo.model.Student;
import com.university.demo.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @GetMapping
    public List<Student> getAllStudents() {
        logger.info("Fetching all students");
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        logger.info("Fetching student with ID: {}", id);
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public Student createStudent(@RequestBody Student student) {
        logger.info("Creating new student: {}", student);
        return studentService.createStudent(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        logger.info("Updating student with ID: {}", id);
        return studentService.getStudentById(id)
                .map(student -> {
                    student.setName(studentDetails.getName());
                    student.setSurname(studentDetails.getSurname());
                    student.setPhoneNumber(studentDetails.getPhoneNumber());
                    Student updatedStudent = studentService.updateStudent(student);
                    return ResponseEntity.ok(updatedStudent);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {
        logger.info("Deleting student with ID: {}", id);
        return studentService.getStudentById(id)
                .map(student -> {
                    studentService.deleteStudent(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
