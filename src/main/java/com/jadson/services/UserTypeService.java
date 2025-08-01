package com.jadson.services;

import com.jadson.dto.requests.UserTypeRequestDTO;
import com.jadson.dto.responses.UserTypeResponseDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface UserTypeService {

    UserTypeResponseDTO findById(Long idUserType);

    List<UserTypeResponseDTO> findAll();

    UserTypeResponseDTO createUserType(UserTypeRequestDTO registerUserTypeDTO);

    UserTypeResponseDTO updateUserType(UserTypeRequestDTO registerUserTypeDTO, Long idUserType);

    String deleteUserType(Long idUserType);

    String deleteAllUserType();

}
