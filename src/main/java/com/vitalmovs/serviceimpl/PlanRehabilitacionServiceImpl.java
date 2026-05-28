package com.vitalmovs.serviceimpl;

import com.vitalmovs.dtos.PlanRehabilitacionDTO;
import com.vitalmovs.entities.Asignacion;
import com.vitalmovs.exceptions.ResourceNotFoundException;
import com.vitalmovs.repositories.AsignacionRepository;
import com.vitalmovs.services.AsignacionService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vitalmovs.entities.PlanRehabilitacion;
import com.vitalmovs.repositories.PlanRehabilitacionRepository;
import com.vitalmovs.services.PlanRehabilitacionService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanRehabilitacionServiceImpl implements PlanRehabilitacionService {

    @Autowired
    private PlanRehabilitacionRepository planRehabilitacionRepository;

    @Autowired
    private AsignacionService asignacionService;

    // ─── add ────────────────────────────────────────────────────────────────

    @Override
    public PlanRehabilitacion add(PlanRehabilitacion planRehabilitacion) {

        if (planRehabilitacion == null) {
            throw new ValidationException("El plan de rehabilitación no puede ser nulo");
        }

        if (planRehabilitacion.getNombre() == null || planRehabilitacion.getNombre().isBlank()) {
            throw new ValidationException("El nombre del plan no puede estar en blanco");
        }

        if (planRehabilitacion.getDescripcion() == null || planRehabilitacion.getDescripcion().isBlank()) {
            throw new ValidationException("La descripción del plan no puede estar en blanco");
        }

        if (planRehabilitacion.getFecha_inicio() == null) {
            throw new ValidationException("La fecha de inicio del plan no puede estar en blanco");
        }

        if (planRehabilitacion.getFecha_fin() == null) {
            throw new ValidationException("La fecha de fin del plan no puede estar en blanco");
        }

        if (planRehabilitacion.getFecha_fin().isBefore(planRehabilitacion.getFecha_inicio())) {
            throw new ValidationException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        if (planRehabilitacion.getEstado() == null || planRehabilitacion.getEstado().isBlank()) {
            throw new ValidationException("El estado del plan no puede estar en blanco");
        }

        if (!planRehabilitacion.getEstado().equalsIgnoreCase("ACTIVO") &&
                !planRehabilitacion.getEstado().equalsIgnoreCase("FINALIZADO") &&
                !planRehabilitacion.getEstado().equalsIgnoreCase("CANCELADO")) {
            throw new ValidationException("El estado del plan debe ser ACTIVO, FINALIZADO o CANCELADO");
        }

        if (planRehabilitacion.getAsignacion() == null || planRehabilitacion.getAsignacion().getId() == null) {
            throw new ValidationException("El plan debe estar asociado a una asignación");
        }
        return planRehabilitacionRepository.save(planRehabilitacion);
    }

    // ─── addDTO ──────────────────────────────────────────────────────────────

    @Override
    public PlanRehabilitacionDTO addDTO(PlanRehabilitacionDTO planRehabilitacionDTO) {
        Asignacion asignacion = asignacionService.findById(planRehabilitacionDTO.getAsignacionId());

        PlanRehabilitacion nuevoPlan = new PlanRehabilitacion(
                null,                                    // id autogenerado
                planRehabilitacionDTO.getNombre(),
                planRehabilitacionDTO.getDescripcion(),
                planRehabilitacionDTO.getFecha_inicio(),
                planRehabilitacionDTO.getFecha_fin(),
                planRehabilitacionDTO.getEstado(),
                asignacion
        );

        nuevoPlan = add(nuevoPlan);
        planRehabilitacionDTO.setId(nuevoPlan.getId());
        return planRehabilitacionDTO;
    }

    // ─── findById ────────────────────────────────────────────────────────────

    @Override
    public PlanRehabilitacion findById(Long id) {
        return planRehabilitacionRepository.findById(id).orElse(null);
    }

    // ─── update ──────────────────────────────────────────────────────────────

    @Override
    public PlanRehabilitacionDTO update(PlanRehabilitacionDTO planRehabilitacionDTO) {
        PlanRehabilitacion foundPlan = planRehabilitacionRepository
                .findById(planRehabilitacionDTO.getId()).orElse(null);
        if (foundPlan == null) {
            throw new ResourceNotFoundException("No se encontro el plan con id: " + planRehabilitacionDTO.getId().toString());
        }
        if (planRehabilitacionDTO.getNombre() != null && !planRehabilitacionDTO.getNombre().isBlank()) {
            foundPlan.setNombre(planRehabilitacionDTO.getNombre());
        }
        if (planRehabilitacionDTO.getDescripcion() != null && !planRehabilitacionDTO.getDescripcion().isBlank()) {
            foundPlan.setDescripcion(planRehabilitacionDTO.getDescripcion());
        }
        if (planRehabilitacionDTO.getFecha_inicio() != null) {
            foundPlan.setFecha_inicio(planRehabilitacionDTO.getFecha_inicio());
        }

        planRehabilitacionRepository.save(foundPlan);
        return toDTO(foundPlan);
    }

    @Override
    public PlanRehabilitacion delete(Long id) {
        PlanRehabilitacion plan = planRehabilitacionRepository.findById(id).orElse(null);
        if (plan == null) {
            throw new ResourceNotFoundException("No se encontro el plan con id: " + id.toString());
        }
        planRehabilitacionRepository.delete(plan);
        return plan;
    }

    @Override
    public List<PlanRehabilitacionDTO> listAllDTO() {
        List<PlanRehabilitacion> planes = planRehabilitacionRepository.findAll();
        List<PlanRehabilitacionDTO> planDTOList = new ArrayList<>();
        for (PlanRehabilitacion p : planes) {
            planDTOList.add(toDTO(p));
        }
        return planDTOList;
    }

    private PlanRehabilitacionDTO toDTO(PlanRehabilitacion p) {
        return new PlanRehabilitacionDTO(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getFecha_inicio(),
                p.getFecha_fin(),
                p.getEstado(),
                p.getAsignacion() != null ? p.getAsignacion().getId() : null
        );
    }

}
