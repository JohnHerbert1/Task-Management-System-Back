package com.jadson.models.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "Error_Logs")
public class ErrorLogEntity {
    @Id
    @Column(name = "ID_Log")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "error_logs_sequence")
    @SequenceGenerator(name = "error_logs_sequence", sequenceName = "Error_Logs_ID_Log_seq", allocationSize = 1)
    private Long idLog;
    @Column(name = "Action_In")
    private String actionIn;
    @Column(name = "Action_Out")
    private String actionOut;
    @Column(name = "Inclusion_Date")
    private Instant inclusionDate;

}
