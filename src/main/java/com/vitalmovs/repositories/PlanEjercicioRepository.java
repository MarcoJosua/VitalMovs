package com.vitalmovs.repositories;

import com.vitalmovs.entities.PlanEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlanEjercicioRepository extends JpaRepository<PlanEjercicio, Long> {
    List<PlanEjercicio> findByPlanRehabilitacion_Id(Long planId);
    List<PlanEjercicio> findByEjercicio_Id(Long ejercicioId);
}