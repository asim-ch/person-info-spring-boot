package com.example.personinfo.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
* This is the custom annotation used to validate date string provided by users as input.
*
* @author  Asim shahzad
* @since   2020-09-15 
*/
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
public @interface ValidDate {
    String message() default "Please provide a valid Date between the range current date and 01-01-1900";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
