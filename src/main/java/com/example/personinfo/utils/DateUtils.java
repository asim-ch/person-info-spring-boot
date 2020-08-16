package com.example.personinfo.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the common utils class to perform date operations.
 *
 * @author Asim shahzad
 * @since 2020-08-16
 */
public class DateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    public static String dateFormat = "dd-MM-yyyy";
    public static String oldDateLimit = "01-01-1900";

    /**
     * This method is used to convert date string to date object by using
     * special format.
     * 
     * @param dateString [String] - date String
     * @return date [Date] - date object
     * @throws Exception 
     */
    public static Date convertStringToDate(String dateString) {
        Date date = null;

        try {
            DateFormat formatter = new SimpleDateFormat(dateFormat);
            date = formatter.parse(dateString);
        } catch (Exception e) {
            LOGGER.error("Error occured while converting string to date, error: " + e.toString());
        }
        
        return date;
    }

    /**
     * This method is used to convert date object to date string by using
     * special format.
     * 
     * @param date [Date] - date object
     * @return dateString [String] - date string
     */
    public static String convertDateToString(Date date) {
        String dateString = "";

        try {
            DateFormat formatter = new SimpleDateFormat(dateFormat);
            dateString = formatter.format(date);
        } catch (Exception e) {
            LOGGER.error("Error occured while converting date to string, error: " + e.toString());
        }

        return dateString;
    }

    /**
     * This method is used to check if date is in valid range.
     * 
     * @param date [Date] - date object
     * @return status [boolean] - status flag
     */
    public static boolean isValidDate(Date date) {
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        boolean status = false;

        try {
            Date limitOldDate = formatter.parse(oldDateLimit);
            if (date.compareTo(new Date()) < 0 && date.compareTo(limitOldDate) > 0) {
                status = true;
            }
         } catch (Exception e) {
            LOGGER.error("Error occured while converting date to string, error: " + e.toString());
         }

        return status;
    }
}
