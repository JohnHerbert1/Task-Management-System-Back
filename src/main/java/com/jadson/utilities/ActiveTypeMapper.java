package com.jadson.utilities;

import com.jadson.dto.requests.ActiveTypeRequestDTO;
import com.jadson.dto.responses.ActiveTypeResponseDTO;
import com.jadson.models.entities.ActiveType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActiveTypeMapper {

    public ActiveType toActiveType(ActiveTypeRequestDTO activeTypeRequestDTO) {
        return ActiveType.builder()
                .description(activeTypeRequestDTO.getDescription())
                .build();
    }

    public ActiveTypeResponseDTO toActiveTypeDTO(ActiveType activeType) { return new ActiveTypeResponseDTO(activeType);
    }

    public List<ActiveTypeResponseDTO> toActiveTypeListDTO(List<ActiveType> activeTypeList) {
        return activeTypeList.stream().map(ActiveTypeResponseDTO::new).collect(Collectors.toList());
    }

    public void updateActiveTypeData(ActiveType activeType, ActiveTypeRequestDTO activeTypeRequestDTO) {
        activeType.setDescription(activeTypeRequestDTO.getDescription());
    }
}
