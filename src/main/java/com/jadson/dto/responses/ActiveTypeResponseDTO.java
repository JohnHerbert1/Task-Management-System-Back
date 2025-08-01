package com.jadson.dto.responses;

import com.jadson.models.entities.ActiveType;
import lombok.Getter;

@Getter
public class ActiveTypeResponseDTO {

    private Long id;

    private String description;

    public ActiveTypeResponseDTO(ActiveType activeType) {
        this.id = activeType.getId();
        this.description = activeType.getDescription();
    }

}
