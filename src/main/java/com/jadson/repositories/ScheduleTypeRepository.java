package com.jadson.repositories;

import com.jadson.models.entities.ScheduleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleTypeRepository extends JpaRepository<ScheduleType, Long> {

}
