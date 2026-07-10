package com.vitalmovs.serviceimpl;

import com.vitalmovs.dtos.PlanEjercicioDTO;
import com.vitalmovs.entities.Ejercicio;
import com.vitalmovs.entities.PlanEjercicio;
import com.vitalmovs.entities.PlanRehabilitacion;
import com.vitalmovs.exceptions.ResourceNotFoundException;
import com.vitalmovs.repositories.PlanEjercicioRepository;
import com.vitalmovs.services.EjercicioService;
import com.vitalmovs.services.PlanEjercicioService;
import com.vitalmovs.services.PlanRehabilitacionService;
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
    private PlanRehabilitacionService planRehabilitacionService;

    @Override
    public PlanEjercicio add(PlanEjercicio planEjercicio) {
        if (planEjercicio.getSeries() == null) {
            throw new ValidationException("Las series no pueden estar vacías");
        }

        if (planEjercicio.getSeries() <= 0) {
            throw new ValidationException("Las series deben ser mayores a cero");
        }

        if (planEjercicio.getRepeticiones() == null) {
            throw new ValidationException("Las repeticiones no pueden estar vacías");
        }

        if (planEjercicio.getRepeticiones() <= 0) {
            throw new ValidationException("Las repeticiones deben ser mayores a cero");
        }

        if (planEjercicio.getDuracionRecomendada() == null) {
            throw new ValidationException("La duración recomendada no puede estar vacía");
        }

        if (planEjercicio.getDuracionRecomendada() <= 0) {
            throw new ValidationException("La duración recomendada debe ser mayor a cero");
        }

        if (planEjercicio.getDiaSemana() == null || planEjercicio.getDiaSemana().isBlank()) {
            throw new ValidationException("El día de la semana no puede estar en blanco");
        }

        if (planEjercicio.getOrden() == null) {
            throw new ValidationException("El orden no puede estar vacío");
        }

        if (planEjercicio.getOrden() <= 0) {
            throw new ValidationException("El orden debe ser mayor a cero");
        }

        planEjercicio = planEjercicioRepository.save(planEjercicio);
        return planEjercicio;
    }

    @Override
    public PlanEjercicioDTO addDTO(PlanEjercicioDTO planEjercicioDTO) {

        PlanRehabilitacion planRehabilitacion = planRehabilitacionService.findById(planEjercicioDTO.getPlanRehabilitacionId());

        Ejercicio ejercicio = ejercicioService.findById(planEjercicioDTO.getEjercicioId());

        PlanEjercicio newPlanEjercicio = new PlanEjercicio(
                null,
                planEjercicioDTO.getSeries(),
                planEjercicioDTO.getRepeticiones(),
                planEjercicioDTO.getDuracionRecomendada(),
                planEjercicioDTO.getDiaSemana(),
                planEjercicioDTO.getOrden(),
                planRehabilitacion,
                ejercicio,
                null
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
    public List<PlanEjercicio> listAll() {
        return planEjercicioRepository.findAll();
    }
    @Override
    public List<PlanEjercicioDTO> listAllDTO() {
        List<PlanEjercicio> planEjercicioList = listAll();
        List<PlanEjercicioDTO> planEjercicioDTOList = new ArrayList<>();
        for (PlanEjercicio pe : planEjercicioList) {
            planEjercicioDTOList.add(new PlanEjercicioDTO(
                    pe.getId(),
                    pe.getSeries(),
                    pe.getRepeticiones(),
                    pe.getDuracionRecomendada(),
                    pe.getDiaSemana(),
                    pe.getOrden(),
                    pe.getPlanRehabilitacion() != null ? pe.getPlanRehabilitacion().getId() : null,
                    pe.getEjercicio() != null ? pe.getEjercicio().getId() : null,
                    pe.getEjercicio() != null ? pe.getEjercicio().getNombre() : null
            ));
        }
        return planEjercicioDTOList;
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
                    pe.getSeries(),
                    pe.getRepeticiones(),
                    pe.getDuracionRecomendada(),
                    pe.getDiaSemana(),
                    pe.getOrden(),
                    pe.getPlanRehabilitacion() != null ? pe.getPlanRehabilitacion().getId() : null,
                    pe.getEjercicio() != null ? pe.getEjercicio().getId() : null,
                    pe.getEjercicio() != null ? pe.getEjercicio().getNombre() : null
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
                    pe.getSeries(),
                    pe.getRepeticiones(),
                    pe.getDuracionRecomendada(),
                    pe.getDiaSemana(),
                    pe.getOrden(),
                    pe.getPlanRehabilitacion() != null ? pe.getPlanRehabilitacion().getId() : null,
                    pe.getEjercicio() != null ? pe.getEjercicio().getId() : null,
                    pe.getEjercicio() != null ? pe.getEjercicio().getNombre() : null
            ));
        }
        return planEjercicioDTOList;
    }

    @Override
    public PlanEjercicioDTO update(
            PlanEjercicioDTO dto) {
        if (dto.getId() == null) {
            throw new ValidationException(
                    "Debe ingresar el id del plan ejercicio"
            );
        }
        PlanEjercicio actual = findById(dto.getId());
        if (actual == null) {
            throw new ResourceNotFoundException(
                    "No se encontró el plan ejercicio con id: "
                            + dto.getId()
            );
        }
        if (
                dto.getSeries() == null ||
                        dto.getSeries() <= 0
        ) {
            throw new ValidationException(
                    "Las series deben ser mayores a cero"
            );
        }
        if (
                dto.getRepeticiones() == null ||
                        dto.getRepeticiones() <= 0
        ) {
            throw new ValidationException(
                    "Las repeticiones deben ser mayores a cero"
            );
        }
        if (
                dto.getDuracionRecomendada() == null ||
                        dto.getDuracionRecomendada() <= 0
        ) {
            throw new ValidationException(
                    "La duración recomendada debe ser mayor a cero"
            );
        }
        if (
                dto.getDiaSemana() == null ||
                        dto.getDiaSemana().isBlank()
        ) {
            throw new ValidationException(
                    "El día de la semana no puede estar en blanco"
            );
        }
        if (
                dto.getOrden() == null ||
                        dto.getOrden() <= 0
        ) {
            throw new ValidationException(
                    "El orden debe ser mayor a cero"
            );
        }
        if (dto.getPlanRehabilitacionId() == null) {
            throw new ValidationException(
                    "Debe ingresar el id del plan de rehabilitación"
            );
        }
        PlanRehabilitacion plan =
                planRehabilitacionService.findById(
                        dto.getPlanRehabilitacionId()
                );
        if (plan == null) {
            throw new ResourceNotFoundException(
                    "No se encontró el plan de rehabilitación con id: "
                            + dto.getPlanRehabilitacionId()
            );
        }
        if (dto.getEjercicioId() == null) {
            throw new ValidationException(
                    "Debe ingresar el id del ejercicio"
            );
        }
        Ejercicio ejercicio =
                ejercicioService.findById(
                        dto.getEjercicioId()
                );
        if (ejercicio == null) {
            throw new ResourceNotFoundException(
                    "No se encontró el ejercicio con id: "
                            + dto.getEjercicioId()
            );
        }
        actual.setSeries(dto.getSeries());
        actual.setRepeticiones(dto.getRepeticiones());
        actual.setDuracionRecomendada(
                dto.getDuracionRecomendada()
        );
        actual.setDiaSemana(dto.getDiaSemana());
        actual.setOrden(dto.getOrden());
        actual.setPlanRehabilitacion(plan);
        actual.setEjercicio(ejercicio);
        PlanEjercicio guardado =
                planEjercicioRepository.save(actual);

        return new PlanEjercicioDTO(
                guardado.getId(),
                guardado.getSeries(),
                guardado.getRepeticiones(),
                guardado.getDuracionRecomendada(),
                guardado.getDiaSemana(),
                guardado.getOrden(),
                guardado.getPlanRehabilitacion().getId(),
                guardado.getEjercicio().getId(),
                guardado.getEjercicio().getNombre()
        );
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new ValidationException("Debe ingresar el id del plan ejercicio");
        }

        PlanEjercicio planEjercicio = findById(id);

        if (planEjercicio == null) {
            throw new ResourceNotFoundException("No se encontro el plan ejercicio con id: " + id);
        }

        planEjercicioRepository.delete(planEjercicio);
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
                    pe.getSeries(),
                    pe.getRepeticiones(),
                    pe.getDuracionRecomendada(),
                    pe.getDiaSemana(),
                    pe.getOrden(),
                    pe.getPlanRehabilitacion() != null ? pe.getPlanRehabilitacion().getId() : null,
                    pe.getEjercicio() != null ? pe.getEjercicio().getId() : null,
                    pe.getEjercicio() != null ? pe.getEjercicio().getNombre() : null
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
                    pe.getSeries(),
                    pe.getRepeticiones(),
                    pe.getDuracionRecomendada(),
                    pe.getDiaSemana(),
                    pe.getOrden(),
                    pe.getPlanRehabilitacion() != null ? pe.getPlanRehabilitacion().getId() : null,
                    pe.getEjercicio() != null ? pe.getEjercicio().getId() : null,
                    pe.getEjercicio() != null ? pe.getEjercicio().getNombre() : null
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
                    pe.getSeries(),
                    pe.getRepeticiones(),
                    pe.getDuracionRecomendada(),
                    pe.getDiaSemana(),
                    pe.getOrden(),
                    pe.getPlanRehabilitacion() != null ? pe.getPlanRehabilitacion().getId() : null,
                    pe.getEjercicio() != null ? pe.getEjercicio().getId() : null,
                    pe.getEjercicio() != null ? pe.getEjercicio().getNombre() : null
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
                    pe.getSeries(),
                    pe.getRepeticiones(),
                    pe.getDuracionRecomendada(),
                    pe.getDiaSemana(),
                    pe.getOrden(),
                    pe.getPlanRehabilitacion() != null ? pe.getPlanRehabilitacion().getId() : null,
                    pe.getEjercicio() != null ? pe.getEjercicio().getId() : null,
                    pe.getEjercicio() != null ? pe.getEjercicio().getNombre() : null
            ));
        }
        return planEjercicioDTOList;
    }
}