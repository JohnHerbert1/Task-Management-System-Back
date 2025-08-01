package com.jadson.services;

import com.jadson.dto.requests.ScheduleRequestDTO;
import com.jadson.dto.responses.ScheduleResponseDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ScheduleService {

    ScheduleResponseDTO findById(Long idSchedule);

    List<ScheduleResponseDTO> findAll();

    ScheduleResponseDTO createSchedule(ScheduleRequestDTO registerScheduleDTO);

    ScheduleResponseDTO updateSchedule(ScheduleRequestDTO registerScheduleDTO, Long idSchedule);

    String deleteSchedule(Long idSchedule);

    String deleteAllSchedule();

}
