package com.jadson.services;

import com.jadson.dto.requests.UserLogRequestDTO;
import com.jadson.dto.responses.UserLogResponseDTO;
import com.jadson.exceptions.BusinessRuleException;
import com.jadson.models.entities.UserLogEntity;
import com.jadson.repositories.UserLogRepository;
import com.jadson.utilities.UserLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLogService implements ServiceInterface<UserLogResponseDTO, UserLogRequestDTO, Pageable> {
    private final UserLogRepository userLogRepository;
    private final UserLogMapper userLogMapper;

    private final String USER_NOT_FOUND = "O registro identificador do usuário, não foi encontrado!";
    private final String NO_LOG_FOUND = "Não existe nenhum log até o momento";
    private final String OCORREU_ERRO_AO_TENTAR_APAGAR_LOG_USUARIO = "Ocorreu um erro ao tentar apagar o log, identificador não existe ou foi apagado.";
    @Override
    public UserLogResponseDTO findByIdEntity(Long id) throws BusinessRuleException {
        Optional<UserLogEntity> userLogEntityResult = Optional.ofNullable(userLogRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException(USER_NOT_FOUND)));
        return userLogMapper.returnResponseDTO(userLogEntityResult.get());
    }

    @Override
    public Page<UserLogResponseDTO> findAllEntity(Pageable pageable) throws BusinessRuleException{
        Optional<Page<UserLogEntity>> userLogEntityPage =
                Optional.ofNullable(Optional.ofNullable(userLogRepository.findAllBy(pageable))
                        .orElseThrow(() -> new BusinessRuleException(NO_LOG_FOUND)));
        return userLogEntityPage.get().map(userLogMapper::returnResponseDTO);
    }

    @Override
    public UserLogResponseDTO registerEntity(UserLogRequestDTO object) {
        return userLogMapper.returnResponseDTO(userLogRepository.save(userLogMapper.returnUserLogEntity(object)));
    }

    @Override
    public UserLogResponseDTO updateEntity(UserLogRequestDTO objectUpdate, Long id) {
        UserLogEntity userLogEntity = userLogMapper.returnUserLogEntity(objectUpdate);
        userLogEntity.setIdLog(id);
        return userLogMapper.returnResponseDTO(userLogRepository.save(userLogEntity));
    }

    @Override
    public Boolean deleteEntity(Long id) throws BusinessRuleException{
        Optional<UserLogEntity> userLogEntity = userLogRepository.findById(id);
        if(userLogEntity.isPresent()){
            userLogRepository.delete(userLogEntity.get());
        }
        else{
            throw new BusinessRuleException(OCORREU_ERRO_AO_TENTAR_APAGAR_LOG_USUARIO);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteAllEntity() {
        userLogRepository.deleteAll();
        return Boolean.TRUE;
    }
}