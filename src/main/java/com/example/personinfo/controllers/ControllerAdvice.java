package com.example.personinfo.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.example.personinfo.dtos.CustomErrorDTO;
import com.example.personinfo.dtos.ValidationErrorDTO;
import com.example.personinfo.exceptions.PersonInfoException;

/**
 * This class is acting as a spring rest controller advice which is used to
 * handle exceptions globally, it is used to avoid defining exception handler in
 * each controller explicitly to handle exceptions.
 * 
 * @author Asim shahzad
 * @since   2020-08-16
 *
 */
@RestControllerAdvice
public class ControllerAdvice {

    /**
     * This method acts as an exception handler to handle custom exceptions
     * which are populated from anywhere in the application.
     * 
     * @param ex [CustomException] - custom exception
     * @param request [WebRequest] - actual web request
     * @return ResponseEntity<CustomErrorDTO>
     */
    @ExceptionHandler(PersonInfoException.class)
    public final ResponseEntity<CustomErrorDTO> handleCustomException(PersonInfoException ex, WebRequest request) {
        CustomErrorDTO customError = ex.getCustomError();
        return new ResponseEntity<CustomErrorDTO>(customError, customError.getStatus());
    }

    /**
     * This method acts as an exception handler to handle System generated exceptions.
     * 
     * @param ex [Exception] - system generated exception
     * @param request [WebRequest] - actual web request
     * @return ResponseEntity<CustomErrorDTO>
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<CustomErrorDTO> handleSystemGeneratedException(Exception ex, WebRequest request) {
        CustomErrorDTO customError = new CustomErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR,
                "An internal server error occured while processing your request", ex.toString());
        return new ResponseEntity<>(customError, customError.getStatus());
    }

    /**
     * This method acts as an exception handler to handle
     * MethodArgumentNotValidException exceptions which will be populated for
     * DTO validation errors, if some attributes do not meet requirements.
     * 
     * @param ex [MethodArgumentNotValidException] - exception
     * @param request [WebRequest] - actual web request
     * @return ResponseEntity<List<ValidationErrorDTO>>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<List<ValidationErrorDTO>> onMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest request) {

        Set<String> filteredErrorFields = new HashSet<>();
        ValidationErrorDTO error = new ValidationErrorDTO();
        List<ValidationErrorDTO> errors = new ArrayList<>();

        ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .filter(violation -> filteredErrorFields.add(violation.getField())).forEach(v -> {
                error.setFieldName(v.getField());
                error.setMessage(v.getDefaultMessage());
                errors.add(error);
            });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * This method acts as an exception handler to handle
     * MissingServletRequestParameterException exceptions which will be
     * populated for query params validation errors, if they do not meet the
     * requirements.
     * 
     * @param ex [MissingServletRequestParameterException] - exception
     * @param request [WebRequest] - actual web request
     * @return ResponseEntity<ValidationErrorDTO>
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ValidationErrorDTO> onConstraintViolationException(
            MissingServletRequestParameterException ex, WebRequest request) {
        ValidationErrorDTO error = new ValidationErrorDTO();
        error.setFieldName(ex.getParameterName());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
