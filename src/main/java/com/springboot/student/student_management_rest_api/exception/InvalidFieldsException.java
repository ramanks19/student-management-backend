package com.springboot.student.student_management_rest_api.exception;

public class InvalidFieldsException extends RuntimeException{
    public InvalidFieldsException(String message) {
        super(message);
    }
}
