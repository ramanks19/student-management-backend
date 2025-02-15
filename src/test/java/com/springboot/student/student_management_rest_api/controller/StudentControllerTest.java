package com.springboot.student.student_management_rest_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.student.student_management_rest_api.payload.StudentDTO;
import com.springboot.student.student_management_rest_api.payload.StudentResponse;
import com.springboot.student.student_management_rest_api.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    void testCreateStudent() throws Exception{
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("John Doe");
        studentDTO.setAge("20");
        studentDTO.setStudentClass("X");
        studentDTO.setPhoneNumber("1234567890");
        when(studentService.addStudent(any())).thenReturn(studentDTO);

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetAllStudents() throws Exception {
        StudentResponse response = new StudentResponse();
        when(studentService.getAllStudents(anyInt(), anyInt(), anyString(), anyString())).thenReturn(response);

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk());
    }

    @Test
    void testSearchByStudentName() throws Exception {
        StudentResponse response = new StudentResponse();
        when(studentService.getAllStudentsWithName(anyString(), anyInt(), anyInt(), anyString(), anyString())).thenReturn(response);

        mockMvc.perform(get("/api/students/search").param("name", "John"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateByStudentName() throws Exception {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("John Doe");
        studentDTO.setAge("20");
        studentDTO.setStudentClass("X");
        studentDTO.setPhoneNumber("1234567890");
        when(studentService.updateStudentByName(any(), anyString(), any())).thenReturn(studentDTO);

        mockMvc.perform(put("/api/students/by-name/John")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateByStudentId() throws Exception {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("John Doe");
        studentDTO.setAge("20");
        studentDTO.setStudentClass("X");
        studentDTO.setPhoneNumber("1234567890");
        when(studentService.updateStudentById(any(), anyLong(), any())).thenReturn(studentDTO);

        mockMvc.perform(put("/api/students/by-id/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteByStudentName() throws Exception {
        doNothing().when(studentService).deleteByStudentName(anyString());

        mockMvc.perform(delete("/api/students/by-name/John"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteByStudentId() throws Exception {
        doNothing().when(studentService).deleteByStudentId(anyLong());

        mockMvc.perform(delete("/api/students/by-id/1"))
                .andExpect(status().isOk());
    }


}
