package com.springboot.student.student_management_rest_api.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudentResponse {

    private List<StudentDTO> student;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public StudentResponse(List<StudentDTO> students, int pageNo, int pageSize, long totalElements,
                           int totalPages, boolean last) {
        this.student =(List<StudentDTO>) students;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }
}
