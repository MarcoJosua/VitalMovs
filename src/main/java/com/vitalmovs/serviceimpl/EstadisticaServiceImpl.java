package com.vitalmovs.serviceimpl;

import com.vitalmovs.dtos.EstadisticaDTO;
import com.vitalmovs.dtos.EstadisticaGraficoDTO;
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

    @Override
    public EstadisticaDTO findByIdDTO(Long id) {
        if (id == null) {
            throw new ValidationException("Debe ingresar el id de la estadística");
        }

        Estadistica estadistica = findById(id);

        if (estadistica == null) {
            throw new ResourceNotFoundException("No se encontró la estadística con id: " + id);
        }

        return toDTO(estadistica);
    }

    // ─── update ──────────────────────────────────────────────────────────────

    @Override
    public EstadisticaDTO update(EstadisticaDTO estadisticaDTO) {

        Estadistica foundEstadistica = estadisticaRepository.findById(estadisticaDTO.getId()).orElse(null);

        if (foundEstadistica == null) {
            throw new ResourceNotFoundException("No se encontro la estadistica con id: " + estadisticaDTO.getId());
        }

        List<Estadistica> estadisticasDelEjercicio = estadisticaRepository.findByPlanEjercicio_Id(foundEstadistica.getPlanEjercicio().getId());

        for (Estadistica e : estadisticasDelEjercicio) {
            if (e.getFecha().equals(estadisticaDTO.getFecha()) &&  !e.getId().equals(estadisticaDTO.getId())
            ) { throw new ValidationException("Ya existe un progreso registrado para este ejercicio en esa fecha");}
        }

        foundEstadistica.setFecha(estadisticaDTO.getFecha());
        foundEstadistica.setNivelDolor(estadisticaDTO.getNivelDolor());
        foundEstadistica.setNivelDificultad(estadisticaDTO.getNivelDificultad());
        foundEstadistica.setRepeticionesRealizadas(estadisticaDTO.getRepeticionesRealizadas());
        foundEstadistica.setDuracionRealizada(estadisticaDTO.getDuracionRealizada());
        foundEstadistica.setObservacion(estadisticaDTO.getObservacion());

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


    // ESTADÍSTICAS NORMALES
    @Override
    public List<EstadisticaDTO> listByPlanEjercicioId(Long planEjercicioId) {
        if (planEjercicioId == null) {
            throw new ValidationException("Debe ingresar el id del plan ejercicio");
        }

        List<Estadistica> estadisticas = estadisticaRepository.findByPlanEjercicio_Id(planEjercicioId);

        List<EstadisticaDTO> estadisticaDTOList = new ArrayList<>();

        for (Estadistica e : estadisticas) {
            estadisticaDTOList.add(toDTO(e));
        }

        return estadisticaDTOList;
    }

    @Override
    public List<EstadisticaDTO> listByPlanRehabilitacionId(Long planId) {
        if (planId == null) {
            throw new ValidationException("Debe ingresar el id del plan de rehabilitación");
        }

        List<Estadistica> estadisticas = estadisticaRepository.listarPorPlanRehabilitacion(planId);

        List<EstadisticaDTO> estadisticaDTOList = new ArrayList<>();

        for (Estadistica e : estadisticas) {
            estadisticaDTOList.add(toDTO(e));
        }

        return estadisticaDTOList;
    }

    // DASHBOARDS
    @Override
    public EstadisticaGraficoDTO resumenGeneralPorPlan(Long planId) {
        if (planId == null) {
            throw new ValidationException("Debe ingresar el id del plan de rehabilitación");
        }
        EstadisticaGraficoDTO resumen = estadisticaRepository.resumenGeneralPorPlan(planId);
        if (resumen == null) {
            return new EstadisticaGraficoDTO(planId, 0.0, 0.0, 0L, 0L, 0L);
        }
        return resumen;
    }

    @Override
    public List<EstadisticaGraficoDTO> resumenPorEjercicio(Long planId) {
        if (planId == null) {
            throw new ValidationException("Debe ingresar el id del plan de rehabilitación");
        }
        return estadisticaRepository.resumenPorEjercicio(planId);
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

}
