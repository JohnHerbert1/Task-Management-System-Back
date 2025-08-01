package com.jadson.utilities;

import com.jadson.dto.requests.ScheduleRequestDTO;
import com.jadson.dto.responses.ScheduleResponseDTO;
import com.jadson.models.entities.Schedule;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduleMapper {

    public Schedule toSchedule(ScheduleRequestDTO scheduleRequestDTO) {
        return Schedule.builder()
                .id_User(scheduleRequestDTO.getId_User())
                .schedule_type_id(scheduleRequestDTO.getSchedule_type_id())
                .active_type_id(scheduleRequestDTO.getActive_type_id())
                .create_date(scheduleRequestDTO.getCreate_date())
                .update_date(scheduleRequestDTO.getUpdate_date())
                .completion_date(scheduleRequestDTO.getCompletion_date())
                .cancellation_date(scheduleRequestDTO.getCancellation_date())
                .build();
    }

    public ScheduleResponseDTO toScheduleDTO(Schedule schedule) { return new ScheduleResponseDTO(schedule);
    }

    public List<ScheduleResponseDTO> toScheduleListDTO(List<Schedule> scheduleList) {
        return scheduleList.stream().map(ScheduleResponseDTO::new).collect(Collectors.toList());
    }

    public void updateScheduleData(Schedule schedule, ScheduleRequestDTO scheduleRequestDTO) {
        schedule.setId_User(scheduleRequestDTO.getId_User());
        schedule.setSchedule_type_id(scheduleRequestDTO.getSchedule_type_id());
        schedule.setActive_type_id(scheduleRequestDTO.getActive_type_id());
        schedule.setCreate_date(scheduleRequestDTO.getCreate_date());
        schedule.setUpdate_date(scheduleRequestDTO.getUpdate_date());
        schedule.setCompletion_date(scheduleRequestDTO.getCompletion_date());
        schedule.setCancellation_date(scheduleRequestDTO.getCancellation_date());
    }

}
