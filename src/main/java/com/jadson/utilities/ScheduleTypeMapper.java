package com.jadson.utilities;

import com.jadson.dto.requests.ScheduleTypeRequestDTO;
import com.jadson.dto.responses.ScheduleTypeResponseDTO;
import com.jadson.models.entities.ScheduleType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduleTypeMapper {

    public ScheduleType toScheduleType(ScheduleTypeRequestDTO scheduleTypeRequestDTO) {
        return ScheduleType.builder()
                .description(scheduleTypeRequestDTO.getDescription())
                .build();
    }

    public ScheduleTypeResponseDTO toScheduleTypeDTO(ScheduleType scheduleType) { return new ScheduleTypeResponseDTO(scheduleType);
    }

    public List<ScheduleTypeResponseDTO> toScheduleTypeListDTO(List<ScheduleType> scheduleTypeList) {
        return scheduleTypeList.stream().map(ScheduleTypeResponseDTO::new).collect(Collectors.toList());
    }

    public void updateScheduleTypeData(ScheduleType scheduleType, ScheduleTypeRequestDTO scheduleTypeRequestDTO) {
        scheduleType.setDescription(scheduleTypeRequestDTO.getDescription());
    }
}
