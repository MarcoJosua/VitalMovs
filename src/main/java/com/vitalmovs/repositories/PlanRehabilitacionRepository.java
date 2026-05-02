package com.vitalmovs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vitalmovs.entities.PlanRehabilitacion;

@Repository
public interface PlanRehabilitacionRepository extends JpaRepository<PlanRehabilitacion,Long> {
}
