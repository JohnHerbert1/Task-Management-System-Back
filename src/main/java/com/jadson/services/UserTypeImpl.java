package com.jadson.services;

import com.jadson.dto.requests.UserTypeRequestDTO;
import com.jadson.dto.responses.UserTypeResponseDTO;
import com.jadson.exceptions.UserTypeNotFoundException;
import com.jadson.models.entities.UserType;
import com.jadson.repositories.UserTypeRepository;
import com.jadson.utilities.UserTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class UserTypeImpl implements UserTypeService{

    private final UserTypeRepository userTypeRepository;

    private final UserTypeMapper userTypeMapper;

    @Override
    public UserTypeResponseDTO findById(Long idUserType) { return userTypeMapper.toUserTypeDTO(returnUserType(idUserType));
    }

    @Override
    public List<UserTypeResponseDTO> findAll() {
        return userTypeMapper.toUserTypeListDTO(userTypeRepository.findAll());
    }

    @Override
    public UserTypeResponseDTO createUserType(UserTypeRequestDTO userTypeRequestDTO) {
        UserType userType = userTypeMapper.toUserType(userTypeRequestDTO);
        return userTypeMapper.toUserTypeDTO(userTypeRepository.save(userType));
    }

    public UserTypeResponseDTO updateUserType(UserTypeRequestDTO userTypeRequestDTO, Long idUserType) {

        UserType existingUserType = userTypeRepository.findById(idUserType)
                .orElseThrow(() -> new RuntimeException("User Type not found for update with ID: " + idUserType));

        if (userTypeRequestDTO.getDescription() != null && !userTypeRequestDTO.getDescription().trim().isEmpty()) {
            existingUserType.setDescription(userTypeRequestDTO.getDescription());
        }

        UserType updatedUserType = userTypeRepository.save(existingUserType);

        return new UserTypeResponseDTO(updatedUserType);
    }

    @Override
    public String deleteUserType(Long idUserType) {
        userTypeRepository.deleteById(idUserType);
        return "User Type id: " + idUserType + " deleted";
    }

    @Override
    public String deleteAllUserType() {
        userTypeRepository.deleteAll();
        return "All User type deleted";
    }

    private UserType returnUserType(Long idUserType) {
        return userTypeRepository.findById(idUserType)
                .orElseThrow(() -> new UserTypeNotFoundException(idUserType));
    }

}
