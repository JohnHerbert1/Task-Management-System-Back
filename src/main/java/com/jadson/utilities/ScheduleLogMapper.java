package com.jadson.utilities;

import com.jadson.dto.requests.ScheduleLogRequestDTO;
import com.jadson.dto.responses.ScheduleLogResponseDTO;
import com.jadson.models.entities.ScheduleLogEntity;
import com.jadson.models.entities.TaskLogEntity;
import org.springframework.stereotype.Component;

@Component
public class ScheduleLogMapper {
    public ScheduleLogResponseDTO returnResponseDTO(ScheduleLogEntity scheduleLogEntity){
        ScheduleLogResponseDTO scheduleLogResponseDTO = new ScheduleLogResponseDTO();
        scheduleLogResponseDTO.setIdLog(scheduleLogEntity.getIdLog());
        scheduleLogResponseDTO.setActionIn(scheduleLogEntity.getActionIn());
        scheduleLogResponseDTO.setActionOut(scheduleLogEntity.getActionOut());
        scheduleLogResponseDTO.setInclusionDate(scheduleLogEntity.getInclusionDate());
        return scheduleLogResponseDTO;
    }

    public ScheduleLogEntity returnScheduleLogEntity(ScheduleLogRequestDTO scheduleLogRequestDTO){
        ScheduleLogEntity scheduleLogEntity = new ScheduleLogEntity();
        scheduleLogEntity.setActionIn(scheduleLogRequestDTO.getActionIn());
        scheduleLogEntity.setActionOut(scheduleLogRequestDTO.getActionOut());
        scheduleLogEntity.setInclusionDate(scheduleLogRequestDTO.getInclusionDate());
        return scheduleLogEntity;
    }
}
