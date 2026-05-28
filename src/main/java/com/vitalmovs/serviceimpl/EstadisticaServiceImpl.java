package com.vitalmovs.serviceimpl;

import com.vitalmovs.dtos.EstadisticaDTO;
import com.vitalmovs.entities.PlanEjercicio;
import com.vitalmovs.exceptions.ResourceNotFoundException;
import com.vitalmovs.repositories.PlanEjercicioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vitalmovs.entities.Estadistica;
import com.vitalmovs.repositories.EstadisticaRepository;
import com.vitalmovs.services.EstadisticaService;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Service
public class EstadisticaServiceImpl implements EstadisticaService {

    @Autowired
    private EstadisticaRepository estadisticaRepository;

    @Autowired
    private PlanEjercicioRepository planEjercicioRepository;

    @Override
    public Estadistica add(Estadistica estadistica) {

        // Fecha
        if (estadistica.getFecha() == null) {
            throw new ValidationException("La fecha de la estadística no puede estar en blanco");
        }

        if (estadistica.getFecha().isAfter(LocalDate.now())) {
            throw new ValidationException("La fecha de la estadística no puede ser futura");
        }

        // Nivel Dolor
        if (estadistica.getNivelDolor() == null) {
            throw new ValidationException("El nivel de dolor no puede estar en blanco");
        }

        if (estadistica.getNivelDolor() < 0 || estadistica.getNivelDolor() > 10) {
            throw new ValidationException("El nivel de dolor debe estar entre 0 y 10");
        }

        //Nivel Dificultad
        if (estadistica.getNivelDificultad() == null) {
            throw new ValidationException("El nivel de dificultad no puede estar en blanco");
        }

        if (estadistica.getNivelDificultad() < 0 || estadistica.getNivelDificultad() > 10) {
            throw new ValidationException("El nivel de dificultad debe estar entre 0 y 10");
        }

        // Repeticiones

        if (estadistica.getRepeticionesRealizadas() == null) {
            throw new ValidationException("Las repeticiones realizadas no pueden estar en blanco");
        }

        if (estadistica.getRepeticionesRealizadas() < 0) {
            throw new ValidationException("Las repeticiones realizadas no pueden ser negativas");
        }

        // Duracion
        if (estadistica.getDuracionRealizada() == null) {
            throw new ValidationException("La duración realizada no puede estar en blanco");
        }

        if (estadistica.getDuracionRealizada() < 0) {
            throw new ValidationException("La duración realizada no puede ser negativa");
        }

        // Observacion
        if (estadistica.getObservacion() == null || estadistica.getObservacion().isBlank()) {
            throw new ValidationException("La observación de la estadística no puede estar en blanco");
        }

        // Asociacion
        if (estadistica.getPlanEjercicio() == null || estadistica.getPlanEjercicio().getId() == null) {
            throw new ValidationException("La estadística debe estar asociada a un plan ejercicio");
        }
        estadistica = estadisticaRepository.save(estadistica);
        return estadistica;
    }

    @Override
    public EstadisticaDTO addDTO(EstadisticaDTO estadisticaDTO) {

        PlanEjercicio planEjercicio = planEjercicioRepository.findById(estadisticaDTO.getPlanEjercicioId()).orElse(null);

        Estadistica nuevaEstadistica = new Estadistica(
                null,                               // id autogenerado
                estadisticaDTO.getFecha(),
                estadisticaDTO.getNivelDolor(),
                estadisticaDTO.getNivelDificultad(),
                estadisticaDTO.getRepeticionesRealizadas(),
                estadisticaDTO.getDuracionRealizada(),
                estadisticaDTO.getObservacion(),
                planEjercicio
        );

        nuevaEstadistica = add(nuevaEstadistica);
        estadisticaDTO.setId(nuevaEstadistica.getId());
        return estadisticaDTO;
    }

    // ─── findById ────────────────────────────────────────────────────────────

    @Override
    public Estadistica findById(Long id) {
        return estadisticaRepository.findById(id).orElse(null);
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

    private EstadisticaDTO toDTO(Estadistica e) {
        return new EstadisticaDTO(
                e.getId(),
                e.getFecha(),
                e.getNivelDolor(),
                e.getNivelDificultad(),
                e.getRepeticionesRealizadas(),
                e.getDuracionRealizada(),
                e.getObservacion(),
                e.getPlanEjercicio() != null ? e.getPlanEjercicio().getId() : null
        );
    }

    /*

    @Override
    public List<Estadistica> findByPlanNative(Long idPlan) {
        return estadisticaRepository.findByPlanNative(idPlan);
    }

    @Override
    public List<HistorialDolorDTO> obtenerHistorialPorPlan(Long idPlan) {
        return estadisticaRepository.obtenerHistorialPorPlan(idPlan);
    }

    */
}
