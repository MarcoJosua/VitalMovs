package com.vitalmovs.repositories;

import com.vitalmovs.dtos.EstadisticaGraficoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vitalmovs.dtos.HistorialDolorDTO;
import com.vitalmovs.entities.Estadistica;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EstadisticaRepository extends JpaRepository<Estadistica, Long> {

    List<Estadistica> findByPlanEjercicio_Id(Long planEjercicioId);

    boolean existsByFechaAndPlanEjercicio_Id(LocalDate fecha, Long planEjercicioId);

    @Query("SELECT e FROM Estadistica e " +
            "WHERE e.planEjercicio.planRehabilitacion.id = :planId " +
            "ORDER BY e.fecha ASC")
    List<Estadistica> listarPorPlanRehabilitacion(@Param("planId") Long planId);

    @Query("SELECT new com.vitalmovs.dtos.EstadisticaGraficoDTO(" +
            "e.planEjercicio.planRehabilitacion.id, " +
            "AVG(e.nivelDolor), " +
            "AVG(e.nivelDificultad), " +
            "SUM(e.repeticionesRealizadas), " +
            "SUM(e.duracionRealizada), " +
            "COUNT(e)) " +
            "FROM Estadistica e " +
            "WHERE e.planEjercicio.planRehabilitacion.id = :planId " +
            "GROUP BY e.planEjercicio.planRehabilitacion.id")
    EstadisticaGraficoDTO resumenGeneralPorPlan(@Param("planId") Long planId);

    @Query("SELECT new com.vitalmovs.dtos.EstadisticaGraficoDTO(" +
            "e.planEjercicio.planRehabilitacion.id, " +
            "e.fecha, " +
            "AVG(e.nivelDolor), " +
            "AVG(e.nivelDificultad), " +
            "SUM(e.repeticionesRealizadas), " +
            "SUM(e.duracionRealizada), " +
            "COUNT(e)) " +
            "FROM Estadistica e " +
            "WHERE e.planEjercicio.planRehabilitacion.id = :planId " +
            "GROUP BY e.planEjercicio.planRehabilitacion.id, e.fecha " +
            "ORDER BY e.fecha ASC")
    List<EstadisticaGraficoDTO> evolucionPorFecha(@Param("planId") Long planId);

    @Query("SELECT new com.vitalmovs.dtos.EstadisticaGraficoDTO(" +
            "e.planEjercicio.planRehabilitacion.id, " +
            "e.planEjercicio.id, " +
            "e.planEjercicio.ejercicio.nombre, " +
            "AVG(e.nivelDolor), " +
            "AVG(e.nivelDificultad), " +
            "SUM(e.repeticionesRealizadas), " +
            "SUM(e.duracionRealizada), " +
            "COUNT(e)) " +
            "FROM Estadistica e " +
            "WHERE e.planEjercicio.planRehabilitacion.id = :planId " +
            "GROUP BY e.planEjercicio.planRehabilitacion.id, " +
            "e.planEjercicio.id, " +
            "e.planEjercicio.ejercicio.nombre, " +
            "e.planEjercicio.orden " +
            "ORDER BY e.planEjercicio.orden ASC")
    List<EstadisticaGraficoDTO> resumenPorEjercicio(@Param("planId") Long planId);

}
