package com.vitalmovs.services;

import com.vitalmovs.dtos.PlanEjercicioDTO;
import com.vitalmovs.entities.PlanEjercicio;
import java.util.List;

public interface PlanEjercicioService {
    PlanEjercicio add(PlanEjercicio planEjercicio);
    PlanEjercicioDTO addDTO(PlanEjercicioDTO planEjercicioDTO);
    PlanEjercicio findById(Long id);
    List<PlanEjercicio> listAll();
    List<PlanEjercicioDTO> listAllDTO();
    List<PlanEjercicio> listByPlanId(Long planId);
    List<PlanEjercicioDTO> listByPlanIdDTO(Long planId);
    List<PlanEjercicio> listByEjercicioId(Long ejercicioId);
    List<PlanEjercicioDTO> listByEjercicioIdDTO(Long ejercicioId);
    PlanEjercicio update(PlanEjercicio planEjercicio);
    void delete(Long id);
    List<PlanEjercicioDTO> buscarPorRepeticionesMayoresOIgualesDTO(Integer repeticiones);
    List<PlanEjercicioDTO> buscarEjerciciosDePlanOrdenadosDTO(Long planId);
    List<PlanEjercicioDTO> buscarPorOrdenNativeDTO(Integer orden);
    List<PlanEjercicioDTO> buscarPorDiaNativeDTO(String dia);
}