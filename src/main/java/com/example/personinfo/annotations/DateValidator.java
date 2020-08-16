package com.example.personinfo.annotations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.personinfo.utils.DateUtils;

/**
* This is the implementation class for our custom date validation annotation, its used to perform
* different validations on the provided date string.
*
* @author  Asim shahzad
* @since   2020-08-16 
*/
public class DateValidator implements ConstraintValidator<ValidDate, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateValidator.class);

    @Override
    public boolean isValid(String dateString, ConstraintValidatorContext cxt) {
        boolean isValidDate = false;

        try {
            DateFormat formatter = new SimpleDateFormat(DateUtils.dateFormat);
            formatter.setLenient(false);
            Date date = formatter.parse(dateString);
            isValidDate = DateUtils.isValidDate(date);
        } catch (ParseException e) {
            LOGGER.error("Error occured while validating date attribute, error: " + e.toString());
            isValidDate = false;
        }

        return isValidDate;
    }
}
