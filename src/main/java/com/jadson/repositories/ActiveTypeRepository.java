package com.jadson.repositories;

import com.jadson.models.entities.ActiveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActiveTypeRepository extends JpaRepository<ActiveType, Long> {

}
