package com.example.personinfo.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is a custom field validations DTO used to present validation errors in a
 * readable form to users.
 *
 * @author Asim shahzad
 * @since 2020-08-15
 */
@Data
@NoArgsConstructor
public class ValidationErrorDTO {

    private String fieldName;
    private String message;
}
