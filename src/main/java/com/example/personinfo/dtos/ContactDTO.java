package com.example.personinfo.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This is the contact DTO to map contact entity and its used to receive/present
 * date to users.
 *
 * @author Asim shahzad
 * @since 2020-08-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ContactDTO extends AuditTableDTO {

    private Integer personId;
    private String name;
    private String surname;
    private String sex;
    private String email;
    private String phone;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthday;
    private Integer age;
}
