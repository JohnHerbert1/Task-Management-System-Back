package com.jadson.services;

import com.jadson.exceptions.BusinessRuleException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface ServiceInterface<E, T, P> {
    E findByIdEntity(Long id) throws BusinessRuleException;

    Page<E> findAllEntity(P p) throws BusinessRuleException;

    E registerEntity(T object) throws BusinessRuleException;

    E updateEntity(T objectUpdate, Long id) throws BusinessRuleException;

    Boolean deleteEntity(Long id) throws BusinessRuleException;

    Boolean deleteAllEntity() throws BusinessRuleException;
}