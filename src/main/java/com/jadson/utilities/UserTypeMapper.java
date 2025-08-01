package com.jadson.utilities;

import com.jadson.dto.requests.UserTypeRequestDTO;
import com.jadson.dto.responses.UserTypeResponseDTO;
import com.jadson.models.entities.UserType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserTypeMapper {

    public UserType toUserType(UserTypeRequestDTO userTypeRequestDTO) {
        return UserType.builder()
                .description(userTypeRequestDTO.getDescription())
                .build();
    }

    public UserTypeResponseDTO toUserTypeDTO(UserType userType) { return new UserTypeResponseDTO(userType);
    }

    public List<UserTypeResponseDTO> toUserTypeListDTO(List<UserType> userTypeList) {
        return userTypeList.stream().map(UserTypeResponseDTO::new).collect(Collectors.toList());
    }

    public void updateUserTypeData(UserType userType, UserTypeRequestDTO userTypeRequestDTO) {
        userType.setDescription(userTypeRequestDTO.getDescription());
    }
}
