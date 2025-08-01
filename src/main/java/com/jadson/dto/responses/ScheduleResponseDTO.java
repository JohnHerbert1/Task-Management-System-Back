package com.jadson.dto.responses;

import com.jadson.models.entities.Schedule;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDTO {

    private Long id;

    private Long id_User;

    private Long schedule_type_id;

    private Long active_type_id;

    private LocalDateTime create_date;

    private LocalDateTime update_date;

    private LocalDateTime completion_date;

    private LocalDateTime cancellation_date;

    public ScheduleResponseDTO(Schedule schedule) {
        this.id = schedule.getId();
        this.id_User = schedule.getId_User();
        this.schedule_type_id = schedule.getSchedule_type_id();
        this.active_type_id = schedule.getActive_type_id();
        this.create_date = schedule.getCreate_date();
        this.update_date = schedule.getUpdate_date();
        this.completion_date = schedule.getCompletion_date();
        this.cancellation_date = schedule.getCancellation_date();
    }
}
