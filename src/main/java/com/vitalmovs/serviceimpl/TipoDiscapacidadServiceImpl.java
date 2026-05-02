package com.vitalmovs.serviceimpl;
 
import com.vitalmovs.dtos.TipoDiscapacidadDTO;
import com.vitalmovs.entities.TipoDiscapacidad;
import com.vitalmovs.exceptions.ResourceNotFoundException;
import com.vitalmovs.repositories.TipoDiscapacidadRepository;
import com.vitalmovs.services.TipoDiscapacidadService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.ArrayList;
import java.util.List;
 
@Service
public class TipoDiscapacidadServiceImpl implements TipoDiscapacidadService {
 
    @Autowired
    private TipoDiscapacidadRepository tipoDiscapacidadRepository;
 
    @Override
    public TipoDiscapacidad add(TipoDiscapacidad tipoDiscapacidad) {
        if (tipoDiscapacidad.getNombre().isBlank()) {
            throw new ValidationException("El nombre del tipo de discapacidad no puede estar en blanco");
        }
        if (!tipoDiscapacidadRepository.findByNombre(tipoDiscapacidad.getNombre()).isEmpty()) {
            throw new ValidationException("El tipo: " + tipoDiscapacidad.getNombre() + " ya esta registrado");
        }
        tipoDiscapacidad = tipoDiscapacidadRepository.save(tipoDiscapacidad);
        return tipoDiscapacidad;
    }
 
    @Override
    public TipoDiscapacidadDTO addDTO(TipoDiscapacidadDTO tipoDiscapacidadDTO) {
        TipoDiscapacidad newTipo = new TipoDiscapacidad(
                null,
                tipoDiscapacidadDTO.getNombre(),
                tipoDiscapacidadDTO.getDescripcion(),
                null
        );
        newTipo = add(newTipo);
        tipoDiscapacidadDTO.setId(newTipo.getId());
        return tipoDiscapacidadDTO;
    }
 
    @Override
    public TipoDiscapacidad findById(Long id) {
        return tipoDiscapacidadRepository.findById(id).orElse(null);
    }
 
    @Override
    public List<TipoDiscapacidad> listAll() {
        return tipoDiscapacidadRepository.findAll();
    }
 
    @Override
    public List<TipoDiscapacidadDTO> listAllDTO() {
        List<TipoDiscapacidad> tipoList = listAll();
        List<TipoDiscapacidadDTO> tipoDTOList = new ArrayList<>();
        for (TipoDiscapacidad t : tipoList) {
            tipoDTOList.add(new TipoDiscapacidadDTO(
                    t.getId(),
                    t.getNombre(),
                    t.getDescripcion()
            ));
        }
        return tipoDTOList;
    }
 
    @Override
    public TipoDiscapacidad update(TipoDiscapacidad tipoDiscapacidad) {
        TipoDiscapacidad foundTipo = findById(tipoDiscapacidad.getId());
        if (foundTipo == null) {
            throw new ResourceNotFoundException("No se encontro el tipo de discapacidad con id: " + tipoDiscapacidad.getId().toString());
        }
        if (tipoDiscapacidad.getNombre() != null && !tipoDiscapacidad.getNombre().isBlank()) {
            foundTipo.setNombre(tipoDiscapacidad.getNombre());
        }
        if (tipoDiscapacidad.getDescripcion() != null && !tipoDiscapacidad.getDescripcion().isBlank()) {
            foundTipo.setDescripcion(tipoDiscapacidad.getDescripcion());
        }
        tipoDiscapacidad = tipoDiscapacidadRepository.save(foundTipo);
        return tipoDiscapacidad;
    }
 
    @Override
    public void delete(Long id) {
        if (findById(id) == null) {
            throw new ResourceNotFoundException("No se encontro el tipo de discapacidad con id: " + id.toString());
        }
        tipoDiscapacidadRepository.deleteById(id);
    }
}
