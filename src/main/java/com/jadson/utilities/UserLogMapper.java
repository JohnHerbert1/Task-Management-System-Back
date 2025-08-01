package com.jadson.utilities;

import com.jadson.dto.requests.UserLogRequestDTO;
import com.jadson.dto.responses.UserLogResponseDTO;
import com.jadson.models.entities.UserLogEntity;
import org.springframework.stereotype.Component;

@Component
public class UserLogMapper {
    public UserLogResponseDTO returnResponseDTO(UserLogEntity userLogEntity){
        UserLogResponseDTO userLogResponseDTO = new UserLogResponseDTO();
        userLogResponseDTO.setIdLog(userLogEntity.getIdLog());
        userLogResponseDTO.setActionIn(userLogEntity.getActionIn());
        userLogResponseDTO.setActionOut(userLogEntity.getActionOut());
        userLogResponseDTO.setInclusionDate(userLogEntity.getInclusionDate());
        return userLogResponseDTO;
    }

    public UserLogEntity returnUserLogEntity(UserLogRequestDTO userLogRequestDTO){
        UserLogEntity userLogEntity = new UserLogEntity();
        userLogEntity.setActionIn(userLogRequestDTO.getActionIn());
        userLogEntity.setActionOut(userLogRequestDTO.getActionOut());
        userLogEntity.setInclusionDate(userLogRequestDTO.getInclusionDate());
        return userLogEntity;
    }
}
