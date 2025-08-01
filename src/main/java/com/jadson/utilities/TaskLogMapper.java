package com.jadson.utilities;

import com.jadson.dto.requests.TaskLogRequestDTO;
import com.jadson.dto.responses.TaskLogResponseDTO;
import com.jadson.models.entities.TaskLogEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskLogMapper {
    public TaskLogResponseDTO returnResponseDTO(TaskLogEntity taskLogEntity){
        TaskLogResponseDTO taskLogResponseDTO = new TaskLogResponseDTO();
        taskLogResponseDTO.setIdLog(taskLogEntity.getIdLog());
        taskLogResponseDTO.setActionIn(taskLogEntity.getActionIn());
        taskLogResponseDTO.setActionOut(taskLogEntity.getActionOut());
        taskLogResponseDTO.setInclusionDate(taskLogEntity.getInclusionDate());
        return taskLogResponseDTO;
    }

    public TaskLogEntity returnTaskLogEntity(TaskLogRequestDTO taskLogRequestDTO){
        TaskLogEntity taskLogEntity = new TaskLogEntity();
        taskLogEntity.setActionIn(taskLogRequestDTO.getActionIn());
        taskLogEntity.setActionOut(taskLogRequestDTO.getActionOut());
        taskLogEntity.setInclusionDate(taskLogRequestDTO.getInclusionDate());
        return taskLogEntity;
    }
}
