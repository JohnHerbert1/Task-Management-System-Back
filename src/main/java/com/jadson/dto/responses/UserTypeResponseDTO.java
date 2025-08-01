package com.jadson.dto.responses;

import com.jadson.models.entities.UserType;
import lombok.Getter;

@Getter
public class UserTypeResponseDTO {

    private Long id;

    private String description;

    public UserTypeResponseDTO(UserType userType) {
        this.id = userType.getId();
        this.description = userType.getDescription();
    }
}
