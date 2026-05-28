package com.vitalmovs.repositories;

import com.vitalmovs.entities.PlanEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanEjercicioRepository extends JpaRepository<PlanEjercicio, Long> {


    // 1. QUERY METHOD
    // Busca ejercicios asignados a un plan de rehabilitacion
    List<PlanEjercicio> findByPlanRehabilitacion_Id(Long planId);

    // 2. QUERY METHOD
    // Busca planes donde aparece un ejercicio especifico
    List<PlanEjercicio> findByEjercicio_Id(Long ejercicioId);

    // 3. JPQL QUERY
    // Busca planes de ejercicio con repeticiones mayores o iguales a un valor
    @Query("SELECT pe FROM PlanEjercicio pe WHERE pe.repeticiones >= :repeticiones")
    List<PlanEjercicio> buscarPorRepeticionesMayoresOIguales(@Param("repeticiones") Integer repeticiones);

    // 4. JPQL QUERY
    // Busca ejercicios de un plan ordenados por el campo orden
    @Query("SELECT pe FROM PlanEjercicio pe WHERE pe.planRehabilitacion.id = :planId ORDER BY pe.orden ASC")
    List<PlanEjercicio> buscarEjerciciosDePlanOrdenados(@Param("planId") Long planId);

    // 5. NATIVE QUERY
    // Busca planes de ejercicio por orden
    @Query(value = "SELECT * FROM plan_ejercicio WHERE orden = :orden", nativeQuery = true)
    List<PlanEjercicio> buscarPorOrdenNative(@Param("orden") Integer orden);

    // 6. NATIVE QUERY
    // Busca planes de ejercicio por dia (Corregir)
    @Query(value = "SELECT * FROM plan_ejercicio WHERE LOWER(dias) LIKE LOWER(CONCAT('%', :dia, '%'))", nativeQuery = true)
    List<PlanEjercicio> buscarPorDiaNative(@Param("dia") String dia);
}