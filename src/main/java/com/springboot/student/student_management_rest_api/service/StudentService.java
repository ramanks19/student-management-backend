package com.springboot.student.student_management_rest_api.service;

import com.springboot.student.student_management_rest_api.StudentManagementRestApiApplication;
import com.springboot.student.student_management_rest_api.payload.StudentDTO;
import com.springboot.student.student_management_rest_api.payload.StudentResponse;

import java.util.Set;

public interface StudentService {

    StudentDTO addStudent(StudentDTO studentDTO);

    StudentResponse getAllStudents(int pageNo, int pageSize, String sortBy, String sortDir);

    StudentResponse getAllStudentsWithName(String name, int pageNo, int pageSize, String sortBy, String sortDir);

    StudentDTO updateStudentByName(StudentDTO studentDTO, String name, Set<String> fieldsToUpdate);

    StudentDTO updateStudentById(StudentDTO studentDTO, Long studentId, Set<String> fieldsToUpdate);

    void deleteByStudentName(String name);

    void deleteByStudentId(Long studentId);
}
