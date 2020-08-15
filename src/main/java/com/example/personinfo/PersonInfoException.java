package com.example.personinfo;

import com.example.personinfo.dtos.CustomErrorDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* This is the custom exception class which is used to populate custom
* exceptions throughout the application.
*
* @author  Asim shahzad
* @since   2020-08-15 
*/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PersonInfoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private CustomErrorDTO customError;

    public PersonInfoException(CustomErrorDTO customError) {
        super();
        this.customError = customError;
    }
}
