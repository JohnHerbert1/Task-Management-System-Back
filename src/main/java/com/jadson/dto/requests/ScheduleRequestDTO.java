package com.jadson.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleRequestDTO {

    private Long id_User;

    private Long schedule_type_id;

    private Long active_type_id;

    @NotNull(message = "Date of creation is required")
    private LocalDateTime create_date;

    private LocalDateTime update_date;

    private LocalDateTime completion_date;

    private LocalDateTime cancellation_date;
}
