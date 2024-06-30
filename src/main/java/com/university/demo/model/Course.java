package com.university.demo.model;

import jakarta.persistence.*;
import lombok.Data;
//import javax.persistence.*;

@Entity
@Table(name = "courses")
@Data

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

}
