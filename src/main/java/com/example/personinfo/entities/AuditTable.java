package com.example.personinfo.entities;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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

    @CreationTimestamp
    @Column(name = "created", updatable = false)
    private Instant created;

    @UpdateTimestamp
    @Column(name = "modified")
    private Instant modified;
}
