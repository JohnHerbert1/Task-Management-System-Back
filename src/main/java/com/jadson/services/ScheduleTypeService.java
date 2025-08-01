package com.jadson.services;

import com.jadson.dto.requests.ScheduleTypeRequestDTO;
import com.jadson.dto.responses.ScheduleTypeResponseDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ScheduleTypeService {

    ScheduleTypeResponseDTO findById(Long idScheduleType);

    List<ScheduleTypeResponseDTO> findAll();

    ScheduleTypeResponseDTO createScheduleType(ScheduleTypeRequestDTO registerScheduleTypeDTO);

    ScheduleTypeResponseDTO updateScheduleType(ScheduleTypeRequestDTO registerScheduleTypeDTO, Long idScheduleType);

    String deleteScheduleType(Long idScheduleType);

    String deleteAllScheduleType();

}
