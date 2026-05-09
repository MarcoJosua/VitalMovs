package com.vitalmovs.serviceimpl;

import com.vitalmovs.dtos.PlanEjercicioDTO;
import com.vitalmovs.entities.Ejercicio;
import com.vitalmovs.entities.PlanEjercicio;
import com.vitalmovs.entities.PlanRehabilitacion;
import com.vitalmovs.exceptions.ResourceNotFoundException;
import com.vitalmovs.repositories.PlanEjercicioRepository;
import com.vitalmovs.repositories.PlanRehabilitacionRepository;
import com.vitalmovs.services.EjercicioService;
import com.vitalmovs.services.PlanEjercicioService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanEjercicioServiceImpl implements PlanEjercicioService {
    @Autowired
    private PlanEjercicioRepository planEjercicioRepository;
    @Autowired
    private EjercicioService ejercicioService;
    @Autowired
    private PlanRehabilitacionRepository planRehabilitacionRepository;

    @Override
    public PlanEjercicio add(PlanEjercicio planEjercicio) {
        if (planEjercicio.getRepeticiones() == null || planEjercicio.getRepeticiones() <= 0) {
            throw new ValidationException("Las repeticiones deben ser mayores a cero");
        }
        if (planEjercicio.getDias() == null || planEjercicio.getDias().isBlank()) {
            throw new ValidationException("Los dias del plan ejercicio no pueden estar en blanco");
        }
        if (planEjercicio.getOrden() == null || planEjercicio.getOrden() <= 0) {
            throw new ValidationException("El orden debe ser mayor a cero");
        }
        if (planEjercicio.getPlanRehabilitacion() == null || planEjercicio.getPlanRehabilitacion().getId() == null) {
            throw new ValidationException("Debe ingresar un plan de rehabilitacion");
        }
        if (planEjercicio.getEjercicio() == null || planEjercicio.getEjercicio().getId() == null) {
            throw new ValidationException("Debe ingresar un ejercicio");
        }
        planEjercicio = planEjercicioRepository.save(planEjercicio);
        return planEjercicio;
    }

    @Override
    public PlanEjercicioDTO addDTO(PlanEjercicioDTO planEjercicioDTO) {

        if (planEjercicioDTO.getPlanId() == null) {
            throw new ValidationException("Debe ingresar el id del plan de rehabilitacion");
        }
        if (planEjercicioDTO.getEjercicioId() == null) {
            throw new ValidationException("Debe ingresar el id del ejercicio");
        }
        PlanRehabilitacion planRehabilitacion = planRehabilitacionRepository
                .findById(planEjercicioDTO.getPlanId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontro el plan de rehabilitacion con id: " + planEjercicioDTO.getPlanId()
                ));
        Ejercicio ejercicio = ejercicioService.findById(planEjercicioDTO.getEjercicioId());
        if (ejercicio == null) {
            throw new ResourceNotFoundException(
                    "No se encontro el ejercicio con id: " + planEjercicioDTO.getEjercicioId()
            );
        }
        PlanEjercicio newPlanEjercicio = new PlanEjercicio(
                null,
                planEjercicioDTO.getRepeticiones(),
                planEjercicioDTO.getDias(),
                planEjercicioDTO.getOrden(),
                planRehabilitacion,
                ejercicio
        );
        newPlanEjercicio = add(newPlanEjercicio);
        planEjercicioDTO.setId(newPlanEjercicio.getId());
        return planEjercicioDTO;
    }

    @Override
    public PlanEjercicio findById(Long id) {
        return planEjercicioRepository.findById(id).orElse(null);
    }

    @Override
    public List<PlanEjercicio> listByPlanId(Long planId) {
        return planEjercicioRepository.findByPlanRehabilitacion_Id(planId);
    }

    @Override
    public List<PlanEjercicioDTO> listByPlanIdDTO(Long planId) {
        List<PlanEjercicio> planEjercicioList = listByPlanId(planId);
        List<PlanEjercicioDTO> planEjercicioDTOList = new ArrayList<>();

        for (PlanEjercicio pe : planEjercicioList) {
            planEjercicioDTOList.add(new PlanEjercicioDTO(
                    pe.getId(),
                    pe.getRepeticiones(),
                    pe.getDias(),
                    pe.getOrden(),
                    pe.getPlanRehabilitacion().getId(),
                    pe.getEjercicio().getId()
            ));
        }
        return planEjercicioDTOList;
    }

    @Override
    public List<PlanEjercicio> listByEjercicioId(Long ejercicioId) {
        return planEjercicioRepository.findByEjercicio_Id(ejercicioId);
    }

    @Override
    public List<PlanEjercicioDTO> listByEjercicioIdDTO(Long ejercicioId) {
        List<PlanEjercicio> planEjercicioList = listByEjercicioId(ejercicioId);
        List<PlanEjercicioDTO> planEjercicioDTOList = new ArrayList<>();

        for (PlanEjercicio pe : planEjercicioList) {
            planEjercicioDTOList.add(new PlanEjercicioDTO(
                    pe.getId(),
                    pe.getRepeticiones(),
                    pe.getDias(),
                    pe.getOrden(),
                    pe.getPlanRehabilitacion().getId(),
                    pe.getEjercicio().getId()
            ));
        }
        return planEjercicioDTOList;
    }

    @Override
    public PlanEjercicio update(PlanEjercicio planEjercicio) {
        if (planEjercicio.getId() == null) {
            throw new ValidationException("Debe ingresar el id del plan ejercicio");
        }
        PlanEjercicio oldPlanEjercicio = findById(planEjercicio.getId());
        if (oldPlanEjercicio == null) {
            throw new ResourceNotFoundException("No se encontro el plan ejercicio con id: " + planEjercicio.getId());
        }
        if (planEjercicio.getRepeticiones() != null && planEjercicio.getRepeticiones() > 0) {
            oldPlanEjercicio.setRepeticiones(planEjercicio.getRepeticiones());
        }
        if (planEjercicio.getDias() != null && !planEjercicio.getDias().isBlank()) {
            oldPlanEjercicio.setDias(planEjercicio.getDias());
        }
        if (planEjercicio.getOrden() != null && planEjercicio.getOrden() > 0) {
            oldPlanEjercicio.setOrden(planEjercicio.getOrden());
        }
        if (planEjercicio.getPlanRehabilitacion() != null && planEjercicio.getPlanRehabilitacion().getId() != null) {
            PlanRehabilitacion planRehabilitacion = planRehabilitacionRepository
                    .findById(planEjercicio.getPlanRehabilitacion().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "No se encontro el plan de rehabilitacion con id: " + planEjercicio.getPlanRehabilitacion().getId()
                    ));

            oldPlanEjercicio.setPlanRehabilitacion(planRehabilitacion);
        }
        if (planEjercicio.getEjercicio() != null && planEjercicio.getEjercicio().getId() != null) {
            Ejercicio ejercicio = ejercicioService.findById(planEjercicio.getEjercicio().getId());
            if (ejercicio == null) {
                throw new ResourceNotFoundException(
                        "No se encontro el ejercicio con id: " + planEjercicio.getEjercicio().getId()
                );
            }
            oldPlanEjercicio.setEjercicio(ejercicio);
        }
        return planEjercicioRepository.save(oldPlanEjercicio);
    }
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new ValidationException("Debe ingresar el id del plan ejercicio");
        }
        if (findById(id) == null) {
            throw new ResourceNotFoundException("No se encontro el plan ejercicio con id: " + id);
        }
        planEjercicioRepository.deleteById(id);
    }
    @Override
    public List<PlanEjercicioDTO> buscarPorRepeticionesMayoresOIgualesDTO(Integer repeticiones) {
        if (repeticiones == null || repeticiones <= 0) {
            throw new ValidationException("Debe ingresar una cantidad de repeticiones mayor a cero");
        }
        List<PlanEjercicio> planEjercicioList = planEjercicioRepository.buscarPorRepeticionesMayoresOIguales(repeticiones);
        List<PlanEjercicioDTO> planEjercicioDTOList = new ArrayList<>();
        for (PlanEjercicio pe : planEjercicioList) {
            planEjercicioDTOList.add(new PlanEjercicioDTO(
                    pe.getId(),
                    pe.getRepeticiones(),
                    pe.getDias(),
                    pe.getOrden(),
                    pe.getPlanRehabilitacion().getId(),
                    pe.getEjercicio().getId()
            ));
        }
        return planEjercicioDTOList;
    }
    @Override
    public List<PlanEjercicioDTO> buscarEjerciciosDePlanOrdenadosDTO(Long planId) {
        if (planId == null) {
            throw new ValidationException("Debe ingresar el id del plan de rehabilitacion");
        }
        List<PlanEjercicio> planEjercicioList = planEjercicioRepository.buscarEjerciciosDePlanOrdenados(planId);
        List<PlanEjercicioDTO> planEjercicioDTOList = new ArrayList<>();
        for (PlanEjercicio pe : planEjercicioList) {
            planEjercicioDTOList.add(new PlanEjercicioDTO(
                    pe.getId(),
                    pe.getRepeticiones(),
                    pe.getDias(),
                    pe.getOrden(),
                    pe.getPlanRehabilitacion().getId(),
                    pe.getEjercicio().getId()
            ));
        }
        return planEjercicioDTOList;
    }
    @Override
    public List<PlanEjercicioDTO> buscarPorOrdenNativeDTO(Integer orden) {
        if (orden == null || orden <= 0) {
            throw new ValidationException("Debe ingresar un orden mayor a cero");
        }
        List<PlanEjercicio> planEjercicioList = planEjercicioRepository.buscarPorOrdenNative(orden);
        List<PlanEjercicioDTO> planEjercicioDTOList = new ArrayList<>();
        for (PlanEjercicio pe : planEjercicioList) {
            planEjercicioDTOList.add(new PlanEjercicioDTO(
                    pe.getId(),
                    pe.getRepeticiones(),
                    pe.getDias(),
                    pe.getOrden(),
                    pe.getPlanRehabilitacion().getId(),
                    pe.getEjercicio().getId()
            ));
        }
        return planEjercicioDTOList;
    }
    @Override
    public List<PlanEjercicioDTO> buscarPorDiaNativeDTO(String dia) {
        if (dia == null || dia.isBlank()) {
            throw new ValidationException("Debe ingresar un dia");
        }
        List<PlanEjercicio> planEjercicioList = planEjercicioRepository.buscarPorDiaNative(dia);
        List<PlanEjercicioDTO> planEjercicioDTOList = new ArrayList<>();
        for (PlanEjercicio pe : planEjercicioList) {
            planEjercicioDTOList.add(new PlanEjercicioDTO(
                    pe.getId(),
                    pe.getRepeticiones(),
                    pe.getDias(),
                    pe.getOrden(),
                    pe.getPlanRehabilitacion().getId(),
                    pe.getEjercicio().getId()
            ));
        }
        return planEjercicioDTOList;
    }
}