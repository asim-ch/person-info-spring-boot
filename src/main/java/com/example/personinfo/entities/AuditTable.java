package com.example.personinfo.entities;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

/**
 * This is a common entity used to store record's creation/modification dates.
 *
 * @author Asim shahzad
 * @since 2020-08-15
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class AuditTable {

    @CreatedDate
    @Column(name = "created", updatable = false)
    private Instant created;

    @LastModifiedDate
    @Column(name = "modified")
    private Instant modified;
}
