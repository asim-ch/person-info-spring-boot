/**
 * 
 */
package com.example.personinfo.dtos;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
* This is the custom error DTO which is used populate custom errors.
* And also it's used to present errors to users in more readable form. 
*
* @author  Asim shahzad
* @since   2020-08-15 
*/
@Data
@AllArgsConstructor
public class CustomErrorDTO {
	
	private HttpStatus status;
	private String message;
	private String messageDetails;

}
