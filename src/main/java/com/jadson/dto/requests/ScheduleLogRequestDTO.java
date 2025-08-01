package com.jadson.dto.requests;

import lombok.Data;

import java.time.Instant;

@Data
public class ScheduleLogRequestDTO {
    private String actionIn;
    private String actionOut;
    private Instant inclusionDate;
}
