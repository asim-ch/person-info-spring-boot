package com.example.personinfo.dtos;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * This is the person DTO to map person entity and its used to receive/present
 * date to users.
 *
 * @author Asim shahzad
 * @since 2020-08-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PersonDTO extends AuditTableDTO {

    public PersonDTO(PersonDTO person) {
        this.age = person.getAge();
        this.id = person.getId();
        this.name = person.getName();
        this.surname = person.getSurname();
        this.sex = person.getSex();
        this.birthday = person.getBirthday();
        this.email = person.getEmail();
        this.phone = person.getPhone();
    }

    private Integer id;

    @NotEmpty(message = "name can't be null or empty")
    @Size(min = 2, max = 20, message = "name must be grater than 2 and less than 20 characters")
    private String name;

    @NotEmpty(message = "surname can't be null or empty")
    @Size(min = 2, max = 20, message = "surname must be grater than 2 and less than 20 characters")
    private String surname;

    @Size(min = 2, max = 5, message = "sex must be greater than 2 and less than 5 characters")
    private String sex;

    @Email
    private String email;

    @Size(min = 7, max = 20, message = "phone must be grater than 7 and less than 20 characters")
    private String phone;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthday;

    @Min(value = 0, message = "age must be greater than 0")
    @Max(value = 100, message = "age must be less than 100")
    private Integer age;

    private List<ContactDTO> contacts;

}
