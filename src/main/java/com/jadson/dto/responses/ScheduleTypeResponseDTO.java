package com.jadson.dto.responses;

import com.jadson.models.entities.ScheduleType;
import lombok.Getter;

@Getter
public class ScheduleTypeResponseDTO {

    private Long id;

    private String description;

    public ScheduleTypeResponseDTO(ScheduleType scheduleType) {
        this.id = scheduleType.getId();
        this.description = scheduleType.getDescription();
    }

}
