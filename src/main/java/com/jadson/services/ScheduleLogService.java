package com.jadson.services;

import com.jadson.dto.requests.ScheduleLogRequestDTO;
import com.jadson.dto.requests.TaskLogRequestDTO;
import com.jadson.dto.responses.ScheduleLogResponseDTO;
import com.jadson.dto.responses.TaskLogResponseDTO;
import com.jadson.exceptions.BusinessRuleException;
import com.jadson.models.entities.ScheduleLogEntity;
import com.jadson.models.entities.TaskLogEntity;
import com.jadson.repositories.ScheduleLogRepository;
import com.jadson.repositories.TaskLogRepository;
import com.jadson.utilities.ScheduleLogMapper;
import com.jadson.utilities.TaskLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleLogService implements ServiceInterface<ScheduleLogResponseDTO, ScheduleLogRequestDTO, Pageable> {
    private final ScheduleLogRepository scheduleLogRepository;
    private final ScheduleLogMapper scheduleLogMapper;

    private final String SCHEDULE_NOT_FOUND = "O registro identificador da agenda, não foi encontrado!";
    private final String NO_LOG_FOUND = "Não existe nenhum log até o momento";
    private final String AN_ERROR_OCCURRED_WHILE_TRYINGTO_DELETE_SCHEDULE_LOG = "Ocorreu um erro ao tentar apagar o log, identificador não existe ou foi apagado.";
    @Override
    public ScheduleLogResponseDTO findByIdEntity(Long id) throws BusinessRuleException {
        Optional<ScheduleLogEntity> scheduleLogEntityResult = Optional.ofNullable(scheduleLogRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException(SCHEDULE_NOT_FOUND)));
        return scheduleLogMapper.returnResponseDTO(scheduleLogEntityResult.get());
    }

    @Override
    public Page<ScheduleLogResponseDTO> findAllEntity(Pageable pageable) throws BusinessRuleException{
        Optional<Page<ScheduleLogEntity>> scheduleLogEntityPage =
                Optional.ofNullable(Optional.ofNullable(scheduleLogRepository.findAllBy(pageable))
                        .orElseThrow(() -> new BusinessRuleException(NO_LOG_FOUND)));
        return scheduleLogEntityPage.get().map(scheduleLogMapper::returnResponseDTO);
    }

    @Override
    public ScheduleLogResponseDTO registerEntity(ScheduleLogRequestDTO object) {
        return scheduleLogMapper.returnResponseDTO(scheduleLogRepository.save(scheduleLogMapper.returnScheduleLogEntity(object)));
    }

    @Override
    public ScheduleLogResponseDTO updateEntity(ScheduleLogRequestDTO objectUpdate, Long id) {
        ScheduleLogEntity scheduleLogEntity = scheduleLogMapper.returnScheduleLogEntity(objectUpdate);
        scheduleLogEntity.setIdLog(id);
        return scheduleLogMapper.returnResponseDTO(scheduleLogRepository.save(scheduleLogEntity));
    }

    @Override
    public Boolean deleteEntity(Long id) throws BusinessRuleException{
        Optional<ScheduleLogEntity> scheduleLogEntity = scheduleLogRepository.findById(id);
        if(scheduleLogEntity.isPresent()){
            scheduleLogRepository.deleteById(id);
        }
        else{
            throw new BusinessRuleException(AN_ERROR_OCCURRED_WHILE_TRYINGTO_DELETE_SCHEDULE_LOG);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteAllEntity() {
        scheduleLogRepository.deleteAll();
        return Boolean.TRUE;
    }
}