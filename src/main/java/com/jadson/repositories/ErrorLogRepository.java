package com.jadson.repositories;

import com.jadson.models.entities.ErrorLogEntity;
import com.jadson.models.entities.ScheduleLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLogEntity, Long> {

    Page<ErrorLogEntity> findAllBy(Pageable Pageble);
}
