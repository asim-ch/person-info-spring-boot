package com.example.personinfo.dtos;

import java.time.Instant;
import lombok.Data;

/**
 * This is a common DTO used to hold record's creation/modification dates.
 *
 * @author Asim shahzad
 * @since 2020-08-15
 */
@Data
public class AuditTableDTO {

    private Instant created;
    private Instant modified;
}
