package com.springboot.student.student_management_rest_api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {
    String message () default "Name must contain only letters and spaces";
    Class<?> [] groups() default{};
    Class<? extends Payload>[] payload() default {};
}
