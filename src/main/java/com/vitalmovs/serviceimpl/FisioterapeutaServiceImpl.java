package com.vitalmovs.services.impl;

import com.vitalmovs.dtos.FisioterapeutaDTO;
import com.vitalmovs.entities.Fisioterapeuta;
import com.vitalmovs.exceptions.ResourceNotFoundException;
import jakarta.validation.ValidationException;
import com.vitalmovs.repositories.FisioterapeutaRepository;
import com.vitalmovs.services.FisioterapeutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FisioterapeutaServiceImpl implements FisioterapeutaService {

    @Autowired
    private FisioterapeutaRepository fisioterapeutaRepository;

    @Override
    public Fisioterapeuta add(Fisioterapeuta fisioterapeuta) {
        if (fisioterapeuta.getNombre().isBlank())
            throw new ValidationException("El nombre del fisioterapeuta no puede estar en blanco");
        if (fisioterapeuta.getApellido().isBlank())
            throw new ValidationException("El apellido del fisioterapeuta no puede estar en blanco");
        if (fisioterapeuta.getEspecialidad().isBlank())
            throw new ValidationException("La especialidad del fisioterapeuta no puede estar en blanco");
        return fisioterapeutaRepository.save(fisioterapeuta);
    }

    @Override
    public FisioterapeutaDTO addDTO(FisioterapeutaDTO dto) {
        Fisioterapeuta f = new Fisioterapeuta(null, dto.getNombre(), dto.getApellido(), dto.getEspecialidad(), null);
        f = add(f);
        dto.setId(f.getId());
        return dto;
    }

    @Override
    public List<Fisioterapeuta> listAll() {
        return fisioterapeutaRepository.findAll();
    }

    @Override
    public List<FisioterapeutaDTO> listAllDTO() {
        List<FisioterapeutaDTO> dtoList = new ArrayList<>();
        for (Fisioterapeuta f : listAll()) {
            dtoList.add(new FisioterapeutaDTO(f.getId(), f.getNombre(), f.getApellido(), f.getEspecialidad()));
        }
        return dtoList;
    }

    @Override
    public Fisioterapeuta findById(Long id) {
        return fisioterapeutaRepository.findById(id).orElse(null);
    }

    @Override
    public Fisioterapeuta update(Fisioterapeuta fisioterapeuta) {
        Fisioterapeuta found = findById(fisioterapeuta.getId());
        if (found == null)
            throw new ResourceNotFoundException("No se encontró el fisioterapeuta con id: " + fisioterapeuta.getId());
        if (fisioterapeuta.getNombre() != null && !fisioterapeuta.getNombre().isBlank())
            found.setNombre(fisioterapeuta.getNombre());
        if (fisioterapeuta.getApellido() != null && !fisioterapeuta.getApellido().isBlank())
            found.setApellido(fisioterapeuta.getApellido());
        if (fisioterapeuta.getEspecialidad() != null && !fisioterapeuta.getEspecialidad().isBlank())
            found.setEspecialidad(fisioterapeuta.getEspecialidad());
        return fisioterapeutaRepository.save(found);
    }

    @Override
    public void delete(Long id) {
        if (findById(id) == null)
            throw new ResourceNotFoundException("No se encontró el fisioterapeuta con id: " + id);
        fisioterapeutaRepository.deleteById(id);
    }
}
