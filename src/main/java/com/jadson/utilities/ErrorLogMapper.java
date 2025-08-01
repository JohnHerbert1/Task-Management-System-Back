package com.jadson.utilities;

import com.jadson.dto.requests.ErrorLogRequestDTO;
import com.jadson.dto.requests.ScheduleLogRequestDTO;
import com.jadson.dto.responses.ErrorLogResponseDTO;
import com.jadson.models.entities.ErrorLogEntity;
import com.jadson.models.entities.ScheduleLogEntity;
import org.springframework.stereotype.Component;

@Component
public class ErrorLogMapper {
    public ErrorLogResponseDTO returnResponseDTO(ErrorLogEntity errorLogEntity){
        ErrorLogResponseDTO errorLogResponseDTO = new ErrorLogResponseDTO();
        errorLogResponseDTO.setIdLog(errorLogEntity.getIdLog());
        errorLogResponseDTO.setActionIn(errorLogEntity.getActionIn());
        errorLogResponseDTO.setActionOut(errorLogEntity.getActionOut());
        errorLogResponseDTO.setInclusionDate(errorLogEntity.getInclusionDate());
        return errorLogResponseDTO;
    }

    public ErrorLogEntity returnErrorLogEntity(ErrorLogRequestDTO errorLogRequestDTO){
        ErrorLogEntity errorLogEntity = new ErrorLogEntity();
        errorLogEntity.setActionIn(errorLogRequestDTO.getActionIn());
        errorLogEntity.setActionOut(errorLogRequestDTO.getActionOut());
        errorLogEntity.setInclusionDate(errorLogRequestDTO.getInclusionDate());
        return errorLogEntity;
    }
}
