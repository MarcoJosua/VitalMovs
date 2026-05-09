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
                ejercicioDTO.getDescripcion(),
                null
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
        if (id == null) {
            return null;
        }
        return ejercicioRepository.findById(id).orElse(null);
    }
    @Override
    public Ejercicio update(Ejercicio ejercicio) {
        if (ejercicio.getId() == null) {
            throw new ValidationException("Debe ingresar el id del ejercicio");
        }
        Ejercicio foundEjercicio = findById(ejercicio.getId());
        if (foundEjercicio == null) {
            throw new ResourceNotFoundException("No se encontro el ejercicio con id: " + ejercicio.getId());
        }
        if (ejercicio.getNombre() != null && !ejercicio.getNombre().isBlank()) {
            List<Ejercicio> ejerciciosConMismoNombre = ejercicioRepository.findByNombre(ejercicio.getNombre());
            if (!ejerciciosConMismoNombre.isEmpty()
                    && !ejerciciosConMismoNombre.get(0).getId().equals(ejercicio.getId())) {
                throw new ValidationException("El ejercicio: " + ejercicio.getNombre() + " ya esta registrado");
            }
            foundEjercicio.setNombre(ejercicio.getNombre());
        }
        if (ejercicio.getDescripcion() != null && !ejercicio.getDescripcion().isBlank()) {
            foundEjercicio.setDescripcion(ejercicio.getDescripcion());
        }
        return ejercicioRepository.save(foundEjercicio);
    }
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new ValidationException("Debe ingresar el id del ejercicio");
        }
        if (findById(id) == null) {
            throw new ResourceNotFoundException("No se encontro el ejercicio con id: " + id);
        }
        ejercicioRepository.deleteById(id);
    }
    @Override
    public List<EjercicioDTO> buscarPorNombreODescripcionDTO(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new ValidationException("Debe ingresar un texto de busqueda");
        }
        List<Ejercicio> ejercicioList = ejercicioRepository.buscarPorNombreODescripcion(texto);
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
    public List<EjercicioDTO> buscarPorNombreNativeDTO(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new ValidationException("Debe ingresar el nombre del ejercicio");
        }
        List<Ejercicio> ejercicioList = ejercicioRepository.buscarPorNombreNative(nombre);
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
}