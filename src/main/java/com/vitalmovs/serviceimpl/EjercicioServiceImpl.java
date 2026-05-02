package com.vitalmovs.serviceimpl;

import com.vitalmovs.dtos.EjercicioDTO;
import com.vitalmovs.entities.Ejercicio;
import com.vitalmovs.exceptions.ResourceNotFoundException;
import com.vitalmovs.repositories.EjercicioRepository;
import com.vitalmovs.services.EjercicioService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EjercicioServiceImpl implements EjercicioService {
    @Autowired
    private EjercicioRepository ejercicioRepository;

    @Override
    public Ejercicio add(Ejercicio ejercicio) {
        if (ejercicio.getNombre() == null || ejercicio.getNombre().isBlank()) {
            throw new ValidationException("El nombre del ejercicio no puede estar en blanco");
        }
        if (ejercicio.getDescripcion() == null || ejercicio.getDescripcion().isBlank()) {
            throw new ValidationException("La descripcion del ejercicio no puede estar en blanco");
        }
        if (!ejercicioRepository.findByNombre(ejercicio.getNombre()).isEmpty()) {
            throw new ValidationException("El ejercicio: " + ejercicio.getNombre() + " ya esta registrado");
        }
        ejercicio = ejercicioRepository.save(ejercicio);
        return ejercicio;
    }

    @Override
    public EjercicioDTO addDTO(EjercicioDTO ejercicioDTO) {
        Ejercicio newEjercicio = new Ejercicio(
                null,
                ejercicioDTO.getNombre(),
                ejercicioDTO.getDescripcion()
        );
        newEjercicio = add(newEjercicio);
        ejercicioDTO.setId(newEjercicio.getId());
        return ejercicioDTO;
    }

    @Override
    public List<Ejercicio> listAll() {
        return ejercicioRepository.findAll();
    }
    @Override
    public List<EjercicioDTO> listAllDTO() {
        List<Ejercicio> ejercicioList = listAll();
        List<EjercicioDTO> ejercicioDTOList = new ArrayList<>();
        for (Ejercicio e : ejercicioList) {
            ejercicioDTOList.add(new EjercicioDTO(
                    e.getId(),
                    e.getNombre(),
                    e.getDescripcion()
            ));
        }
        return ejercicioDTOList;
    }

    @Override
    public Ejercicio findById(Long id) {
        return ejercicioRepository.findById(id).orElse(null);
    }
    @Override
    public Ejercicio update(Ejercicio ejercicio) {
        Ejercicio foundEjercicio = findById(ejercicio.getId());
        if (foundEjercicio == null) {
            throw new ResourceNotFoundException("No se encontro el ejercicio con id: " + ejercicio.getId().toString());
        }
        if (ejercicio.getNombre() != null && !ejercicio.getNombre().isBlank()) {
            foundEjercicio.setNombre(ejercicio.getNombre());
        }
        if (ejercicio.getDescripcion() != null && !ejercicio.getDescripcion().isBlank()) {
            foundEjercicio.setDescripcion(ejercicio.getDescripcion());
        }
        ejercicio = ejercicioRepository.save(foundEjercicio);
        return ejercicio;
    }
    @Override
    public void delete(Long id) {
        if (findById(id) == null) {
            throw new ResourceNotFoundException("No se encontro el ejercicio con id: " + id.toString());
        }
        ejercicioRepository.deleteById(id);
    }
}