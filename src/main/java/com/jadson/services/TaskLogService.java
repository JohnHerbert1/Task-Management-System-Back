package com.jadson.services;

import com.jadson.dto.requests.TaskLogRequestDTO;
import com.jadson.dto.responses.TaskLogResponseDTO;
import com.jadson.exceptions.BusinessRuleException;
import com.jadson.models.entities.TaskLogEntity;
import com.jadson.repositories.TaskLogRepository;
import com.jadson.utilities.TaskLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskLogService implements ServiceInterface<TaskLogResponseDTO, TaskLogRequestDTO, Pageable> {
    private final TaskLogRepository taskLogRepository;
    private final TaskLogMapper taskLogMapper;

    private final String TASK_NOT_FOUND = "O registro identificador da tarefa, não foi encontrado!";
    private final String NO_LOG_FOUND = "Não existe nenhum log até o momento";
    private final String AN_ERROR_OCCURRED_WHILE_TRYINGTO_DELETE_TASK_LOG = "Ocorreu um erro ao tentar apagar o log, identificador não existe ou foi apagado.";
    @Override
    public TaskLogResponseDTO findByIdEntity(Long id) throws BusinessRuleException {
        Optional<TaskLogEntity> userLogEntityResult = Optional.ofNullable(taskLogRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException(TASK_NOT_FOUND)));
        return taskLogMapper.returnResponseDTO(userLogEntityResult.get());
    }

    @Override
    public Page<TaskLogResponseDTO> findAllEntity(Pageable pageable) throws BusinessRuleException{
        Optional<Page<TaskLogEntity>> taskLogEntityPage =
                Optional.ofNullable(Optional.ofNullable(taskLogRepository.findAllBy(pageable))
                        .orElseThrow(() -> new BusinessRuleException(NO_LOG_FOUND)));
        return taskLogEntityPage.get().map(taskLogMapper::returnResponseDTO);
    }

    @Override
    public TaskLogResponseDTO registerEntity(TaskLogRequestDTO object) {
        return taskLogMapper.returnResponseDTO(taskLogRepository.save(taskLogMapper.returnTaskLogEntity(object)));
    }

    @Override
    public TaskLogResponseDTO updateEntity(TaskLogRequestDTO objectUpdate, Long id) {
        TaskLogEntity taskLogEntity = taskLogMapper.returnTaskLogEntity(objectUpdate);
        taskLogEntity.setIdLog(id);
        return taskLogMapper.returnResponseDTO(taskLogRepository.save(taskLogEntity));
    }

    @Override
    public Boolean deleteEntity(Long id) throws BusinessRuleException{
        Optional<TaskLogEntity> taskLogEntity = taskLogRepository.findById(id);
        if(taskLogEntity.isPresent()){
            taskLogRepository.deleteById(id);
        }
        else{
            throw new BusinessRuleException(AN_ERROR_OCCURRED_WHILE_TRYINGTO_DELETE_TASK_LOG);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteAllEntity() {
        taskLogRepository.deleteAll();
        return Boolean.TRUE;
    }
}