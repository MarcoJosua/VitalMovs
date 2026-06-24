package com.vitalmovs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vitalmovs.entities.PlanRehabilitacion;

import java.util.List;

@Repository
public interface PlanRehabilitacionRepository extends JpaRepository<PlanRehabilitacion,Long> {

    @Query("""
    SELECT p
    FROM PlanRehabilitacion p
    WHERE p.asignacion.paciente.user.id = :userId
       OR p.asignacion.fisioterapeuta.user.id = :userId
    """)
    List<PlanRehabilitacion> findByUserId(@Param("userId") Long userId);


}
