package com.springboot.student.student_management_rest_api.controller;

import com.springboot.student.student_management_rest_api.payload.StudentDTO;
import com.springboot.student.student_management_rest_api.payload.StudentResponse;
import com.springboot.student.student_management_rest_api.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Add a student
     * */

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        return new ResponseEntity<>(studentService.addStudent(studentDTO), HttpStatus.CREATED);
    }

    /**
     * List all the students
     * */

    @GetMapping
    public ResponseEntity<StudentResponse> getAllStudents(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(studentService.getAllStudents(pageNo, pageSize, sortBy, sortDir));
    }

    /**
     * Search the student by its name
     * */
    @GetMapping("/search")
    public ResponseEntity<StudentResponse> searchByStudentName(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(studentService.getAllStudentsWithName(name, pageNo, pageSize, sortBy, sortDir));
    }

    /**
     * Update a student using its name.
     * */

    @PutMapping("/by-name/{name}")
    public ResponseEntity<StudentDTO> updateStudent(@Valid @RequestBody StudentDTO studentDTO,
                                                    @PathVariable String name,
                                                    @RequestParam(required = false)Set<String> fieldsToUpdate) {
        StudentDTO studentResponse = studentService.updateStudentByName(studentDTO, name, fieldsToUpdate);
        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }

    /**
     * Update a student using its student id
     * */
    @PutMapping("/by-id/{studentId}")
    public ResponseEntity<StudentDTO> updateStudent(@Valid @RequestBody StudentDTO studentDTO,
                                                    @PathVariable Long studentId,
                                                    @RequestParam(required = false)Set<String> fieldsToUpdate) {
        StudentDTO studentResponse = studentService.updateStudentById(studentDTO, studentId, fieldsToUpdate);
        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }

    /**
     * Delete a student using its name
     * */
    @DeleteMapping("/by-name/{name}")
    public ResponseEntity<String> deleteStudentByName(@PathVariable String name) {
        studentService.deleteByStudentName(name);
        return new ResponseEntity<>("Student with name: " + name + " is deleted successfully!!!", HttpStatus.OK);
    }

    /**
     * Delete a student using its student id
     * */
    @DeleteMapping("/by-id/{studentId}")
    public ResponseEntity<String> deleteStudentById(@PathVariable Long studentId) {
        studentService.deleteByStudentId(studentId);
        return new ResponseEntity<>("Student with id: " + studentId + " is deleted successfully!!!", HttpStatus.OK);
    }
}
