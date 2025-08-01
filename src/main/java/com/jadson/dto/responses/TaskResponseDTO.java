package com.jadson.dto.responses;

import java.time.LocalDate;
import java.time.LocalTime;

import com.jadson.models.entities.Task;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TaskResponseDTO {

    private Long id;

    private String title;

    private String description;

    private LocalDate date;

    private LocalTime hour;

    public TaskResponseDTO(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.date = task.getDate();
        this.hour = task.getHour();
    }
    
}
