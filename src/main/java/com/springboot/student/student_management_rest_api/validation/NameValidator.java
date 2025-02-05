package com.springboot.student.student_management_rest_api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {
    private static final String NAME_PATTERN = "^[A-Za-z]+(?:[.][A-Za-z]+)*([ ][A-Za-z]+)*$";

    @Override
    public void initialize(ValidName constraintAnnotation) {

    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name != null && name.matches(NAME_PATTERN);
    }
}
