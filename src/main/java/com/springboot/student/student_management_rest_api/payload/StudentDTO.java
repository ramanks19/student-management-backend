package com.springboot.student.student_management_rest_api.payload;

import com.springboot.student.student_management_rest_api.validation.ValidName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Long studentId;

    @ValidName
    @NotBlank(message = "Name of the student cannot be blank")
    private String name;

    @NotBlank(message = "Age of the student cannot be blank")
    private String age;

    @NotBlank(message = "Class of the student cannot be blank")
    private String studentClass;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "\\d{10}", message = "Phone number should be of 10 digits")
    private String phoneNumber;

}
