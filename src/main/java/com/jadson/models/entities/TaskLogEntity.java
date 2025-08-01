package com.jadson.models.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "Task_Logs")
public class TaskLogEntity {
    @Id
    @Column(name = "ID_Log")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_logs_sequence")
    @SequenceGenerator(name = "task_logs_sequence", sequenceName = "Task_Logs_ID_Log_seq", allocationSize = 1)
    private Long idLog;
    @Column(name = "Action_In")
    private String actionIn;
    @Column(name = "Action_Out")
    private String actionOut;
    @Column(name = "Inclusion_Date")
    private Instant inclusionDate;

}
