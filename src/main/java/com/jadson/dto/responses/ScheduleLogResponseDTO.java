package com.jadson.dto.responses;

import lombok.Data;

import java.time.Instant;

@Data
public class ScheduleLogResponseDTO {
    private Long idLog;
    private String actionIn;
    private String actionOut;
    private Instant inclusionDate;
}