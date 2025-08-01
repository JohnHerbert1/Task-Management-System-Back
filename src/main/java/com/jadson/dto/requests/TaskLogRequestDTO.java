package com.jadson.dto.requests;

import lombok.Data;

import java.time.Instant;

@Data
public class TaskLogRequestDTO {
    private String actionIn;
    private String actionOut;
    private Instant inclusionDate;
}
