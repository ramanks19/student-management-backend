package com.springboot.student.student_management_rest_api.entity;

import com.springboot.student.student_management_rest_api.validation.ValidName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "STUDENTS")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_ID", nullable = false)
    private Long studentId;

    @ValidName
    @NotBlank(message = "Name of the student cannot be blank")
    @Column(name = "NAME")
    private String name;

    @NotBlank(message = "Age of the student cannot be blank")
    @Column(name = "AGE")
    private String age;

    @NotBlank(message = "Class of the student cannot be blank")
    @Column(name = "STUDENT_CLASS")
    private String studentClass;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "\\d{10}", message = "Phone number should be of 10 digits")
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

}
