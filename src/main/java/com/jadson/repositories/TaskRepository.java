package com.jadson.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jadson.models.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository <Task, Long> {

}
