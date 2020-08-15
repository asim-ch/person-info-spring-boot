package com.example.personinfo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This is the contact entity class to store contact information.
 *
 * @author Asim shahzad
 * @since 2020-08-15
 */
@Entity
@Table(name = "contact")
@Data
@EqualsAndHashCode(callSuper = false)
public class Contact extends AuditTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
