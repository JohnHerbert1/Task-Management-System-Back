package com.jadson.repositories;

import com.jadson.models.entities.Task;
import com.jadson.models.entities.UserLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogRepository extends JpaRepository<UserLogEntity, Long> {

    Page<UserLogEntity> findAllBy(Pageable Pageble);
}
