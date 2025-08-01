package com.jadson.services;

import com.jadson.dto.requests.ScheduleTypeRequestDTO;
import com.jadson.dto.responses.ScheduleTypeResponseDTO;
import com.jadson.exceptions.ScheduleTypeNotFoundException;
import com.jadson.models.entities.ScheduleType;
import com.jadson.repositories.ScheduleTypeRepository;
import com.jadson.utilities.ScheduleTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class ScheduleTypeImpl implements ScheduleTypeService{

    private final ScheduleTypeRepository scheduleTypeRepository;

    private final ScheduleTypeMapper scheduleTypeMapper;

    @Override
    public ScheduleTypeResponseDTO findById(Long idScheduleType) { return scheduleTypeMapper.toScheduleTypeDTO(returnScheduleType(idScheduleType));
    }

    @Override
    public List<ScheduleTypeResponseDTO> findAll() {
        return scheduleTypeMapper.toScheduleTypeListDTO(scheduleTypeRepository.findAll());
    }

    @Override
    public ScheduleTypeResponseDTO createScheduleType(ScheduleTypeRequestDTO scheduleTypeRequestDTO) {
        ScheduleType scheduleType = scheduleTypeMapper.toScheduleType(scheduleTypeRequestDTO);
        return scheduleTypeMapper.toScheduleTypeDTO(scheduleTypeRepository.save(scheduleType));
    }

    public ScheduleTypeResponseDTO updateScheduleType(ScheduleTypeRequestDTO scheduleTypeRequestDTO, Long idScheduleType) {

        ScheduleType existingScheduleType = scheduleTypeRepository.findById(idScheduleType)
                .orElseThrow(() -> new RuntimeException("Schedule Type not found for update with ID: " + idScheduleType));

        if (scheduleTypeRequestDTO.getDescription() != null && !scheduleTypeRequestDTO.getDescription().trim().isEmpty()) {
            existingScheduleType.setDescription(scheduleTypeRequestDTO.getDescription());
        }

        ScheduleType updatedScheduleType = scheduleTypeRepository.save(existingScheduleType);

        return new ScheduleTypeResponseDTO(updatedScheduleType);
    }

    @Override
    public String deleteScheduleType(Long idScheduleType) {
        scheduleTypeRepository.deleteById(idScheduleType);
        return "Schedule Type id: " + idScheduleType + " deleted";
    }

    @Override
    public String deleteAllScheduleType() {
        scheduleTypeRepository.deleteAll();
        return "All schedule type deleted";
    }

    private ScheduleType returnScheduleType(Long idScheduleType) {
        return scheduleTypeRepository.findById(idScheduleType)
                .orElseThrow(() -> new ScheduleTypeNotFoundException(idScheduleType));
    }

}
