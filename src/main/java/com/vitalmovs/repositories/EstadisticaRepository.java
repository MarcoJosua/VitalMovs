package com.vitalmovs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vitalmovs.dtos.HistorialDolorDTO;
import com.vitalmovs.entities.Estadistica;

import java.util.List;

@Repository
public interface EstadisticaRepository extends JpaRepository<Estadistica, Long> {
    @Query("SELECT new com.vitalmovs.dtos.HistorialDolorDTO(p.nombre,e.fecha, e.nivelDolor,e.observacion) " +
            "FROM Estadistica e JOIN e.planRehabilitacion p " +
            "WHERE p.id = :idPlan " +
            "ORDER BY e.fecha DESC")
    List<HistorialDolorDTO> obtenerHistorialPorPlan(@Param("idPlan") Long idPlan);

    @Query(value = "SELECT * FROM estadistica WHERE plan_rehabilitacion_id = :idPlan " +
            "ORDER BY fecha DESC", nativeQuery = true)
    List<Estadistica> findByPlanNative(@Param("idPlan") Long idPlan);

}
