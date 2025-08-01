package com.jadson.repositories;

import com.jadson.models.entities.TaskLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskLogRepository extends JpaRepository<TaskLogEntity, Long> {

    Page<TaskLogEntity> findAllBy(Pageable Pageble);
}
