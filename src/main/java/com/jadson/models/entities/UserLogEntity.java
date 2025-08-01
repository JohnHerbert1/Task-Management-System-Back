package com.jadson.models.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "User_Logs")
public class UserLogEntity {
    @Id
    @Column(name = "ID_Log")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_logs_sequence")
    @SequenceGenerator(name = "user_logs_sequence", sequenceName = "User_Logs_ID_Log_seq", allocationSize = 1)
    private Long idLog;
    @Column(name = "Action_In")
    private String actionIn;
    @Column(name = "Action_Out")
    private String actionOut;
    @Column(name = "Inclusion_Date")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private Instant inclusionDate;

}
