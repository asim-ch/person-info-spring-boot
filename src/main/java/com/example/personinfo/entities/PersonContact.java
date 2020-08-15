package com.example.personinfo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This is the person contact entity class to map relationship between person
 * and contact.
 *
 * @author Asim shahzad
 * @since 2020-08-15
 */
@Entity
@Table(name = "person_contact")
@Data
@EqualsAndHashCode(callSuper = false)
public class PersonContact extends AuditTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;
}
