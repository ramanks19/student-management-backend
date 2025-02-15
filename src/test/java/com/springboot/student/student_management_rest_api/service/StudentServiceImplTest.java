package com.springboot.student.student_management_rest_api.service;

import com.springboot.student.student_management_rest_api.entity.Student;
import com.springboot.student.student_management_rest_api.exception.StudentNotFoundException;
import com.springboot.student.student_management_rest_api.payload.StudentDTO;
import com.springboot.student.student_management_rest_api.payload.StudentResponse;
import com.springboot.student.student_management_rest_api.repository.StudentRepository;
import com.springboot.student.student_management_rest_api.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private StudentDTO studentDTO;

    @BeforeEach
    void setUp() {
        student = new Student(1L, "John Doe", "20", "X", "1234567890");
        studentDTO = new StudentDTO(1L, "John Doe", "20", "X", "1234567890");
    }

    @Test
    void testAddStudent_Success() {
        when(modelMapper.map(studentDTO, Student.class)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(modelMapper.map(student, StudentDTO.class)).thenReturn(studentDTO);

        StudentDTO result = studentService.addStudent(studentDTO);
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testGetAllStudentsWithName_Success() {
        Page<Student> studentPage = new PageImpl<>(List.of(student));
        when(studentRepository.findByNameContainingIgnoreCase(eq("John"), any(Pageable.class))).thenReturn(studentPage);
        when(modelMapper.map(any(Student.class), eq(StudentDTO.class))).thenReturn(studentDTO);

        StudentResponse response = studentService.getAllStudentsWithName("John", 0, 5, "name", "asc");
        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
    }

    @Test
    void testGetAllStudentsWithName_NotFound() {
        when(studentRepository.findByNameContainingIgnoreCase(eq("Jane"), any(Pageable.class))).thenReturn(Page.empty());
        assertThrows(StudentNotFoundException.class, () -> studentService.getAllStudentsWithName("Jane", 0, 5, "name", "asc"));
    }

    @Test
    void testUpdateStudentByName_Success() {
        when(studentRepository.findByName("John Doe")).thenReturn(List.of(student));
        when(studentRepository.save(student)).thenReturn(student);
        when(modelMapper.map(student, StudentDTO.class)).thenReturn(studentDTO);

        StudentDTO result = studentService.updateStudentByName(studentDTO, "John Doe", Set.of("age"));
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testUpdateStudentByName_NotFound() {
        when(studentRepository.findByName("John Doe")).thenReturn(Collections.emptyList());
        assertThrows(StudentNotFoundException.class, () -> studentService.updateStudentByName(studentDTO, "John Doe", Set.of("age")));
    }

    @Test
    void testUpdateStudentById_Success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);
        when(modelMapper.map(student, StudentDTO.class)).thenReturn(studentDTO);

        StudentDTO result = studentService.updateStudentById(studentDTO, 1L, Set.of("age"));
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testUpdateStudentById_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class, () -> studentService.updateStudentById(studentDTO, 1L, Set.of("age")));
    }

    @Test
    void testDeleteByStudentName_Success() {
        when(studentRepository.findByName("John Doe")).thenReturn(List.of(student));
        doNothing().when(studentRepository).delete(student);

        assertDoesNotThrow(() -> studentService.deleteByStudentName("John Doe"));
    }

    @Test
    void testDeleteByStudentName_NotFound() {
        when(studentRepository.findByName("John Doe")).thenReturn(Collections.emptyList());

        assertThrows(StudentNotFoundException.class, () -> studentService.deleteByStudentName("John Doe"));
    }

    @Test
    void testDeleteByStudentId_Success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);

        assertDoesNotThrow(() -> studentService.deleteByStudentId(1L));
    }

    @Test
    void testDeleteByStudentById_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.deleteByStudentId(1L));
    }
}
