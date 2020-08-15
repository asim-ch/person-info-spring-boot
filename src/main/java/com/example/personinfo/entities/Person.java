package com.example.personinfo.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This is the person entity class to store person information.
 *
 * @author Asim shahzad
 * @since 2020-08-15
 */
@Entity
@Table(name = "person")
@Data
@EqualsAndHashCode(callSuper = false)
public class Person extends AuditTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "surname", nullable = false, length = 30)
    private String surname;

    @Column(name = "age", nullable = true, length = 20)
    private int age;

    @Column(name = "sex", nullable = true, length = 10)
    private String sex;

    @Column(name = "birthday", nullable = true, length = 20)
    private Date birthday;

    @Column(name = "phone", nullable = true, length = 50)
    private String phone;

    @Column(name = "email", nullable = true, length = 50)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person", orphanRemoval = true)
    private List<PersonContact> personContacts;
}
