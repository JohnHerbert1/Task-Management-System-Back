package com.jadson.repositories;

import com.jadson.models.entities.ScheduleLogEntity;
import com.jadson.models.entities.TaskLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleLogRepository extends JpaRepository<ScheduleLogEntity, Long> {

    Page<ScheduleLogEntity> findAllBy(Pageable Pageble);
}
