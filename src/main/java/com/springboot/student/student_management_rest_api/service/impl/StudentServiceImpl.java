package com.springboot.student.student_management_rest_api.service.impl;

import com.springboot.student.student_management_rest_api.entity.Student;
import com.springboot.student.student_management_rest_api.exception.InvalidFieldsException;
import com.springboot.student.student_management_rest_api.exception.StudentNotFoundException;
import com.springboot.student.student_management_rest_api.payload.StudentDTO;
import com.springboot.student.student_management_rest_api.payload.StudentResponse;
import com.springboot.student.student_management_rest_api.repository.StudentRepository;
import com.springboot.student.student_management_rest_api.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private ModelMapper modelMapper;
    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Adds a new student
     * */
    @Override
    public StudentDTO addStudent(StudentDTO studentDTO) {
        Student student = mapToEntity(studentDTO);
        Student newStudent = studentRepository.save(student);
        StudentDTO studentResponse =mapToDTO(newStudent);

        return studentResponse;
    }

    /**
     * Search all students
     * */
    @Override
    public StudentResponse getAllStudents(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Student> studentPage = studentRepository.findAll(pageable);

        List<StudentDTO> studentDTOList = studentPage.getContent()
                .stream()
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());

        return new StudentResponse(studentDTOList, studentPage.getNumber(),
                studentPage.getSize(), studentPage.getTotalElements(),
                studentPage.getTotalPages(), studentPage.isLast());
    }

    /**
     * Search a student by name
     * */
    @Override
    public StudentResponse getAllStudentsWithName(String name, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Student> studentPage = studentRepository.findByNameContainingIgnoreCase(name, pageable);

        if (studentPage.isEmpty()) {
            throw new StudentNotFoundException("No students found with name starting with: " + name);
        }

        List<StudentDTO> studentDTOS = studentPage.getContent()
                .stream()
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());

        return new StudentResponse(studentDTOS, studentPage.getNumber(),
                studentPage.getSize(), studentPage.getTotalElements(),
                studentPage.getTotalPages(), studentPage.isLast());
    }

    /**
     * Delete a student by using his name
     * */
    @Override
    public StudentDTO updateStudentByName(StudentDTO studentDTO, String name, Set<String> fieldsToUpdate) {
        List<Student> studentList = studentRepository.findByName(name);
        if (studentList.isEmpty()) {
            throw new StudentNotFoundException("No student found with student name: " + name);
        }

        if (studentList.size() > 1) {
            throw new StudentNotFoundException("Multiple students found with name: " + name + ". Please use Update by Id API to update the student.");
        }

        Student student = studentList.get(0);

        //Get all the valid fields of the student class
        Set<String> validFields = Arrays.stream(Student.class.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toSet());
        if (!validFields.containsAll(fieldsToUpdate)) {
            Set<String> invalidFields = fieldsToUpdate.stream()
                    .filter(field -> !validFields.contains(field))
                    .collect(Collectors.toSet());
            throw new InvalidFieldsException("Invalid Fields: " + invalidFields);
        }

        if (fieldsToUpdate.contains("age")) {
            student.setAge(studentDTO.getAge());
        }

        if (fieldsToUpdate.contains("studentClass")) {
            student.setStudentClass(studentDTO.getStudentClass());
        }

        if (fieldsToUpdate.contains("phoneNumber")) {
            student.setPhoneNumber(studentDTO.getPhoneNumber());
        }

        Student updatedStudent = studentRepository.save(student);

        return modelMapper.map(updatedStudent, StudentDTO.class);
    }

    /**
     * Delete a student by using his id
     * */
    @Override
    public StudentDTO updateStudentById(StudentDTO studentDTO, Long studentId, Set<String> fieldsToUpdate) {
        Optional<Student> studentList = studentRepository.findById(studentId);
        if (studentList.isEmpty()) {
            throw new StudentNotFoundException("No student found with student id: " + studentId);
        }

        Student student = studentList.get();

        //Get all the valid fields of the student class
        Set<String> validFields = Arrays.stream(Student.class.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toSet());
        if (!validFields.containsAll(fieldsToUpdate)) {
            Set<String> invalidFields = fieldsToUpdate.stream()
                    .filter(field -> !validFields.contains(field))
                    .collect(Collectors.toSet());
            throw new InvalidFieldsException("Invalid Fields: " + invalidFields);
        }

        if (fieldsToUpdate.contains("name")) {
            student.setName(studentDTO.getName());
        }

        if (fieldsToUpdate.contains("age")) {
            student.setAge(studentDTO.getAge());
        }

        if (fieldsToUpdate.contains("studentClass")) {
            student.setStudentClass(studentDTO.getStudentClass());
        }

        if (fieldsToUpdate.contains("phoneNumber")) {
            student.setPhoneNumber(studentDTO.getPhoneNumber());
        }

        Student updatedStudent = studentRepository.save(student);

        return modelMapper.map(updatedStudent, StudentDTO.class);
    }

    /**
     * Delete a student using its name
     * */
    @Override
    public void deleteByStudentName(String name) {
        List<Student> studentList = studentRepository.findByName(name);

        if (studentList.isEmpty()) {
            throw new StudentNotFoundException("No Student found with student name: " + name);
        }

        if (studentList.size() > 1) {
            throw new StudentNotFoundException("Multiple students found with name: " + name + ". Please use Delete by Id API to delete the student.");
        }

        studentRepository.delete(studentList.get(0));
    }

    /**
     * Delete a student by using its name
     * */
    @Override
    public void deleteByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("No student found with student id: " + studentId));
        studentRepository.delete(student);
    }

    //Convert DTO to entity
    private Student mapToEntity(StudentDTO studentDTO) {
        Student student = modelMapper.map(studentDTO, Student.class);
        return student;
    }

    //Convert entity to DTO
    private StudentDTO mapToDTO(Student student) {
        StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
        return studentDTO;
    }
}
