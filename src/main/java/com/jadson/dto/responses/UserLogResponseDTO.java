package com.jadson.dto.responses;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
@Data
public class UserLogResponseDTO {
    private Long idLog;
    private String actionIn;
    private String actionOut;
    private Instant inclusionDate;
}