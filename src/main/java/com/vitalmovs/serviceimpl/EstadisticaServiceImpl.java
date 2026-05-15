package com.vitalmovs.serviceimpl;

import com.vitalmovs.dtos.EstadisticaDTO;
import com.vitalmovs.entities.PlanRehabilitacion;
import com.vitalmovs.exceptions.ResourceNotFoundException;
import com.vitalmovs.repositories.PlanRehabilitacionRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vitalmovs.dtos.HistorialDolorDTO;
import com.vitalmovs.entities.Estadistica;
import com.vitalmovs.repositories.EstadisticaRepository;
import com.vitalmovs.services.EstadisticaService;

import java.util.List;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstadisticaServiceImpl implements EstadisticaService {
    @Autowired
    private EstadisticaRepository estadisticaRepository;

    @Autowired
    private PlanRehabilitacionRepository planRehabilitacionRepository;

    // ─── add ────────────────────────────────────────────────────────────────

    @Override
    public Estadistica add(Estadistica estadistica) {
        if (estadistica.getFecha() == null) {
            throw new ValidationException("La fecha de la estadistica no puede estar en blanco");
        }
        if (estadistica.getObservacion().isBlank()) {
            throw new ValidationException("La observacion de la estadistica no puede estar en blanco");
        }
        return estadisticaRepository.save(estadistica);
    }

    // ─── addDTO ──────────────────────────────────────────────────────────────

    @Override
    public EstadisticaDTO addDTO(EstadisticaDTO estadisticaDTO) {
        PlanRehabilitacion planRehabilitacion = planRehabilitacionRepository
                .findById(estadisticaDTO.getPlanRehabilitacionId()).orElse(null);

        Estadistica nuevaEstadistica = new Estadistica(
                null,                               // id autogenerado
                estadisticaDTO.getFecha(),
                estadisticaDTO.getNivelDolor(),
                estadisticaDTO.getObservacion(),
                planRehabilitacion
        );

        nuevaEstadistica = add(nuevaEstadistica);
        estadisticaDTO.setId(nuevaEstadistica.getId());
        return estadisticaDTO;
    }

    // ─── findById ────────────────────────────────────────────────────────────

    @Override
    public EstadisticaDTO findById(Long id) {
        Estadistica estadistica = estadisticaRepository.findById(id).orElse(null);
        if (estadistica == null) {
            throw new ResourceNotFoundException("No se encontro la estadistica con id: " + id.toString());
        }
        return toDTO(estadistica);
    }

    // ─── update ──────────────────────────────────────────────────────────────

    @Override
    public EstadisticaDTO update(EstadisticaDTO estadisticaDTO) {
        Estadistica foundEstadistica = estadisticaRepository.findById(estadisticaDTO.getId()).orElse(null);
        if (foundEstadistica == null) {
            throw new ResourceNotFoundException("No se encontro la estadistica con id: " + estadisticaDTO.getId().toString());
        }
        if (estadisticaDTO.getFecha() != null) {
            foundEstadistica.setFecha(estadisticaDTO.getFecha());
        }
        if (estadisticaDTO.getObservacion() != null && !estadisticaDTO.getObservacion().isBlank()) {
            foundEstadistica.setObservacion(estadisticaDTO.getObservacion());
        }
        // nivelDolor es int (primitivo), siempre se actualiza si viene en el DTO
        foundEstadistica.setNivelDolor(estadisticaDTO.getNivelDolor());

        estadisticaRepository.save(foundEstadistica);
        return toDTO(foundEstadistica);
    }

    // ─── delete ──────────────────────────────────────────────────────────────

    @Override
    public Estadistica delete(Long id) {
        Estadistica estadistica = estadisticaRepository.findById(id).orElse(null);
        if (estadistica == null) {
            throw new ResourceNotFoundException("No se encontro la estadistica con id: " + id.toString());
        }
        estadisticaRepository.delete(estadistica);
        return estadistica;
    }

    // ─── findAll ─────────────────────────────────────────────────────────────

    @Override
    public List<Estadistica> findAll() {
        return estadisticaRepository.findAll();
    }

    // ─── listAllDTO ──────────────────────────────────────────────────────────

    @Override
    public List<EstadisticaDTO> listAllDTO() {
        List<Estadistica> estadisticaList = findAll();
        List<EstadisticaDTO> estadisticaDTOList = new ArrayList<>();
        for (Estadistica e : estadisticaList) {
            estadisticaDTOList.add(toDTO(e));
        }
        return estadisticaDTOList;
    }

    // ─── Helper: entidad → DTO ───────────────────────────────────────────────

    private EstadisticaDTO toDTO(Estadistica e) {
        return new EstadisticaDTO(
                e.getId(),
                e.getFecha(),
                e.getNivelDolor(),
                e.getObservacion(),
                e.getPlanRehabilitacion() != null ? e.getPlanRehabilitacion().getId() : null
        );
    }

    @Override
    public List<Estadistica> findByPlanNative(Long idPlan) {
        return estadisticaRepository.findByPlanNative(idPlan);
    }

    @Override
    public List<HistorialDolorDTO> obtenerHistorialPorPlan(Long idPlan) {
        return estadisticaRepository.obtenerHistorialPorPlan(idPlan);
    }

}
