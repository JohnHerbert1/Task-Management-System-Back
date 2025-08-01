package com.jadson.services;

import com.jadson.dto.requests.ScheduleRequestDTO;
import com.jadson.dto.responses.ScheduleResponseDTO;
import com.jadson.exceptions.ScheduleNotFoundException;
import com.jadson.models.entities.Schedule;
import com.jadson.repositories.ScheduleRepository;
import com.jadson.utilities.ScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class ScheduleImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    @Override
    public ScheduleResponseDTO findById(Long idSchedule) { return scheduleMapper.toScheduleDTO(returnSchedule(idSchedule));
    }

    @Override
    public List<ScheduleResponseDTO> findAll() {
        return scheduleMapper.toScheduleListDTO(scheduleRepository.findAll());
    }

    @Override
    public ScheduleResponseDTO createSchedule(ScheduleRequestDTO scheduleRequestDTO) {
        Schedule schedule = scheduleMapper.toSchedule(scheduleRequestDTO);
        return scheduleMapper.toScheduleDTO(scheduleRepository.save(schedule));
    }

    public ScheduleResponseDTO updateSchedule(ScheduleRequestDTO scheduleRequestDTO, Long idSchedule) {

        Schedule existingSchedule = scheduleRepository.findById(idSchedule)
                .orElseThrow(() -> new RuntimeException("Schedule not found for update with ID: " + idSchedule));

        if (scheduleRequestDTO.getId_User() != null) {
            existingSchedule.setId_User(scheduleRequestDTO.getId_User());
        }
        if (scheduleRequestDTO.getSchedule_type_id() != null) {
            existingSchedule.setSchedule_type_id(scheduleRequestDTO.getSchedule_type_id());
        }
        if (scheduleRequestDTO.getActive_type_id() != null) {
            existingSchedule.setActive_type_id(scheduleRequestDTO.getActive_type_id());
        }
        if (scheduleRequestDTO.getCreate_date() != null) {
            existingSchedule.setCreate_date(scheduleRequestDTO.getCreate_date());
        }
        if (scheduleRequestDTO.getUpdate_date() != null) {
            existingSchedule.setUpdate_date(scheduleRequestDTO.getUpdate_date());
        }
        if (scheduleRequestDTO.getCompletion_date() != null) {
            existingSchedule.setCompletion_date(scheduleRequestDTO.getCompletion_date());
        }
        if (scheduleRequestDTO.getCancellation_date() != null) {
            existingSchedule.setCancellation_date(scheduleRequestDTO.getCancellation_date());
        }

        Schedule updatedSchedule = scheduleRepository.save(existingSchedule);

        return new ScheduleResponseDTO(updatedSchedule);
    }

    @Override
    public String deleteSchedule(Long idSchedule) {
        scheduleRepository.deleteById(idSchedule);
        return "Schedule id: " + idSchedule + " deleted";
    }

    @Override
    public String deleteAllSchedule() {
        scheduleRepository.deleteAll();
        return "All schedule deleted";
    }

    private Schedule returnSchedule(Long idSchedule) {
        return scheduleRepository.findById(idSchedule)
                .orElseThrow(() -> new ScheduleNotFoundException(idSchedule));
    }

}
