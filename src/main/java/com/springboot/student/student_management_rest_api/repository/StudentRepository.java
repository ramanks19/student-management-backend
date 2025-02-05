package com.springboot.student.student_management_rest_api.repository;

import com.springboot.student.student_management_rest_api.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Student> findByName(String name);
}
