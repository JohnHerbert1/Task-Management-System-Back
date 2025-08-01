package com.jadson.services;

import com.jadson.dto.requests.ActiveTypeRequestDTO;
import com.jadson.dto.responses.ActiveTypeResponseDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ActiveTypeService {

    ActiveTypeResponseDTO findById(Long idActiveType);

    List<ActiveTypeResponseDTO> findAll();

    ActiveTypeResponseDTO createActiveType(ActiveTypeRequestDTO registerActiveTypeDTO);

    ActiveTypeResponseDTO updateActiveType(ActiveTypeRequestDTO registerActiveTypeDTO, Long idActiveType);

    String deleteActiveType(Long idActiveType);

    String deleteAllActiveType();

}
