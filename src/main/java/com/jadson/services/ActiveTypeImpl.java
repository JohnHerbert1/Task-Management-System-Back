package com.jadson.services;

import com.jadson.dto.requests.ActiveTypeRequestDTO;
import com.jadson.dto.responses.ActiveTypeResponseDTO;
import com.jadson.exceptions.ActiveTypeNotFoundException;
import com.jadson.models.entities.ActiveType;
import com.jadson.repositories.ActiveTypeRepository;
import com.jadson.utilities.ActiveTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class ActiveTypeImpl implements ActiveTypeService{

    private final ActiveTypeRepository activeTypeRepository;

    private final ActiveTypeMapper activeTypeMapper;

    @Override
    public ActiveTypeResponseDTO findById(Long idActiveType) { return activeTypeMapper.toActiveTypeDTO(returnActiveType(idActiveType));
    }

    @Override
    public List<ActiveTypeResponseDTO> findAll() {
        return activeTypeMapper.toActiveTypeListDTO(activeTypeRepository.findAll());
    }

    @Override
    public ActiveTypeResponseDTO createActiveType(ActiveTypeRequestDTO activeTypeRequestDTO) {
        ActiveType activeType = activeTypeMapper.toActiveType(activeTypeRequestDTO);
        return activeTypeMapper.toActiveTypeDTO(activeTypeRepository.save(activeType));
    }

    public ActiveTypeResponseDTO updateActiveType(ActiveTypeRequestDTO activeTypeRequestDTO, Long idActiveType) {

        ActiveType existingActiveType = activeTypeRepository.findById(idActiveType)
                .orElseThrow(() -> new RuntimeException("Active Type not found for update with ID: " + idActiveType));

        if (activeTypeRequestDTO.getDescription() != null && !activeTypeRequestDTO.getDescription().trim().isEmpty()) {
            existingActiveType.setDescription(activeTypeRequestDTO.getDescription());
        }

        ActiveType updatedActiveType = activeTypeRepository.save(existingActiveType);

        return new ActiveTypeResponseDTO(updatedActiveType);
    }

    @Override
    public String deleteActiveType(Long idActiveType) {
        activeTypeRepository.deleteById(idActiveType);
        return "Active Type id: " + idActiveType + " deleted";
    }

    @Override
    public String deleteAllActiveType() {
        activeTypeRepository.deleteAll();
        return "All active type deleted";
    }

    private ActiveType returnActiveType(Long idActiveType) {
        return activeTypeRepository.findById(idActiveType)
                .orElseThrow(() -> new ActiveTypeNotFoundException(idActiveType));
    }

}
