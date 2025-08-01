package com.jadson.services;

import com.jadson.dto.requests.ErrorLogRequestDTO;
import com.jadson.dto.requests.ScheduleLogRequestDTO;
import com.jadson.dto.responses.ErrorLogResponseDTO;
import com.jadson.dto.responses.ScheduleLogResponseDTO;
import com.jadson.exceptions.BusinessRuleException;
import com.jadson.models.entities.ErrorLogEntity;
import com.jadson.models.entities.ScheduleLogEntity;
import com.jadson.repositories.ErrorLogRepository;
import com.jadson.repositories.ScheduleLogRepository;
import com.jadson.utilities.ErrorLogMapper;
import com.jadson.utilities.ScheduleLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ErrorLogService implements ServiceInterface<ErrorLogResponseDTO, ErrorLogRequestDTO, Pageable> {
    private final ErrorLogRepository errorLogRepository;
    private final ErrorLogMapper errorLogMapper;

    private final String ERROR_NOT_FOUND = "O registro identificador do erro, não foi encontrado!";
    private final String NO_LOG_FOUND = "Não existe nenhum log até o momento";
    private final String AN_ERROR_OCCURRED_WHILE_TRYINGTO_DELETE_THE_LOG = "Ocorreu um erro ao tentar apagar o log, identificador não existe ou foi apagado.";
    @Override
    public ErrorLogResponseDTO findByIdEntity(Long id) throws BusinessRuleException {
        Optional<ErrorLogEntity> errorLogEntityResult = Optional.ofNullable(errorLogRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException(ERROR_NOT_FOUND)));
        return errorLogMapper.returnResponseDTO(errorLogEntityResult.get());
    }

    @Override
    public Page<ErrorLogResponseDTO> findAllEntity(Pageable pageable) throws BusinessRuleException{
        Optional<Page<ErrorLogEntity>> errorLogEntityPage =
                Optional.ofNullable(Optional.ofNullable(errorLogRepository.findAllBy(pageable))
                        .orElseThrow(() -> new BusinessRuleException(NO_LOG_FOUND)));
        return errorLogEntityPage.get().map(errorLogMapper::returnResponseDTO);
    }

    @Override
    public ErrorLogResponseDTO registerEntity(ErrorLogRequestDTO object) {
        return errorLogMapper.returnResponseDTO(errorLogRepository.save(errorLogMapper.returnErrorLogEntity(object)));
    }

    @Override
    public ErrorLogResponseDTO updateEntity(ErrorLogRequestDTO objectUpdate, Long id) {
        ErrorLogEntity errorLogEntity = errorLogMapper.returnErrorLogEntity(objectUpdate);
        errorLogEntity.setIdLog(id);
        return errorLogMapper.returnResponseDTO(errorLogRepository.save(errorLogEntity));
    }

    @Override
    public Boolean deleteEntity(Long id) throws BusinessRuleException{
        Optional<ErrorLogEntity> errorLogEntity = errorLogRepository.findById(id);
        if(errorLogEntity.isPresent()){
            errorLogRepository.deleteById(id);
        }
        else{
            throw new BusinessRuleException(AN_ERROR_OCCURRED_WHILE_TRYINGTO_DELETE_THE_LOG);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteAllEntity() {
        errorLogRepository.deleteAll();
        return Boolean.TRUE;
    }
}