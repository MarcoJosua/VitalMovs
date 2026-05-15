package com.vitalmovs.serviceimpl;

import com.vitalmovs.dtos.FisioterapeutaDiscapacidadDTO;
import com.vitalmovs.entities.Fisioterapeuta;
import com.vitalmovs.entities.FisioterapeutaDiscapacidad;
import com.vitalmovs.entities.TipoDiscapacidad;
import com.vitalmovs.exceptions.ResourceNotFoundException;
import com.vitalmovs.repositories.FisioterapeutaDiscapacidadRepository;
import com.vitalmovs.services.FisioterapeutaDiscapacidadService;
import com.vitalmovs.services.FisioterapeutaService;
import com.vitalmovs.services.TipoDiscapacidadService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FisioterapeutaDiscapacidadServiceImpl implements FisioterapeutaDiscapacidadService {

    @Autowired
    private FisioterapeutaDiscapacidadRepository fdRepository;

    @Autowired
    private FisioterapeutaService fisioterapeutaService;

    @Autowired
    private TipoDiscapacidadService tipoDiscapacidadService;

    @Override
    public FisioterapeutaDiscapacidad add(FisioterapeutaDiscapacidad fd) {
        // Validar que venga el fisioterapeuta vinculado
        if (fd.getFisioterapeuta() == null || fd.getFisioterapeuta().getId() == null)
            throw new ValidationException("El fisioterapeutaId es obligatorio");
        // Validar que venga el tipo de discapacidad vinculado
        if (fd.getTipoDiscapacidad() == null || fd.getTipoDiscapacidad().getId() == null)
            throw new ValidationException("El tipoDiscapacidadId es obligatorio");
        return fdRepository.save(fd);
    }

    @Override
    public FisioterapeutaDiscapacidadDTO addDTO(FisioterapeutaDiscapacidadDTO dto) {
        // Validar que el DTO traiga los IDs antes de buscar en BD
        if (dto.getFisioterapeutaId() == null)
            throw new ValidationException("El fisioterapeutaId no puede ser nulo");
        if (dto.getTipoDiscapacidadId() == null)
            throw new ValidationException("El tipoDiscapacidadId no puede ser nulo");

        Fisioterapeuta fisioterapeuta = fisioterapeutaService.findById(dto.getFisioterapeutaId());
        if (fisioterapeuta == null)
            throw new ResourceNotFoundException("No se encontró el fisioterapeuta con id: " + dto.getFisioterapeutaId());

        TipoDiscapacidad tipoDiscapacidad = tipoDiscapacidadService.findById(dto.getTipoDiscapacidadId());
        if (tipoDiscapacidad == null)
            throw new ResourceNotFoundException("No se encontró el tipo de discapacidad con id: " + dto.getTipoDiscapacidadId());

        FisioterapeutaDiscapacidad fd = new FisioterapeutaDiscapacidad(null, fisioterapeuta, tipoDiscapacidad);
        fd = add(fd);
        dto.setId(fd.getId());
        return dto;
    }

    @Override
    public FisioterapeutaDiscapacidad findById(Long id) {
        // Validar que el id no sea null
        if (id == null)
            throw new ValidationException("El id no puede ser nulo");
        return fdRepository.findById(id).orElse(null);
    }

    @Override
    public List<FisioterapeutaDiscapacidad> listByFisioterapeutaId(Long fisioterapeutaId) {
        // Validar que el fisioterapeutaId no sea null
        if (fisioterapeutaId == null)
            throw new ValidationException("El fisioterapeutaId no puede ser nulo");
        return fdRepository.findByFisioterapeuta_Id(fisioterapeutaId);
    }

    @Override
    public List<FisioterapeutaDiscapacidadDTO> listByFisioterapeutaIdDTO(Long fisioterapeutaId) {
        List<FisioterapeutaDiscapacidadDTO> dtoList = new ArrayList<>();
        for (FisioterapeutaDiscapacidad fd : listByFisioterapeutaId(fisioterapeutaId)) {
            // Validar que los objetos relacionados no sean null antes de llamar getId()
            Long fId = (fd.getFisioterapeuta() != null) ? fd.getFisioterapeuta().getId() : null;
            Long tId = (fd.getTipoDiscapacidad() != null) ? fd.getTipoDiscapacidad().getId() : null;
            dtoList.add(new FisioterapeutaDiscapacidadDTO(fd.getId(), fId, tId));
        }
        return dtoList;
    }

    @Override
    public FisioterapeutaDiscapacidad update(FisioterapeutaDiscapacidad fd) {
        // Validar que venga el id para saber qué registro actualizar
        if (fd.getId() == null)
            throw new ValidationException("El id es obligatorio para actualizar");

        FisioterapeutaDiscapacidad found = findById(fd.getId());
        if (found == null)
            throw new ResourceNotFoundException("No se encontró el registro con id: " + fd.getId());

        // Actualizar fisioterapeuta si viene con id válido
        if (fd.getFisioterapeuta() != null && fd.getFisioterapeuta().getId() != null) {
            Fisioterapeuta fisioterapeuta = fisioterapeutaService.findById(fd.getFisioterapeuta().getId());
            if (fisioterapeuta == null)
                throw new ResourceNotFoundException("No se encontró el fisioterapeuta con id: " + fd.getFisioterapeuta().getId());
            found.setFisioterapeuta(fisioterapeuta);
        }

        // Actualizar tipoDiscapacidad si viene con id válido
        if (fd.getTipoDiscapacidad() != null && fd.getTipoDiscapacidad().getId() != null) {
            TipoDiscapacidad tipoDiscapacidad = tipoDiscapacidadService.findById(fd.getTipoDiscapacidad().getId());
            if (tipoDiscapacidad == null)
                throw new ResourceNotFoundException("No se encontró el tipo de discapacidad con id: " + fd.getTipoDiscapacidad().getId());
            found.setTipoDiscapacidad(tipoDiscapacidad);
        }

        return fdRepository.save(found);
    }

    @Override
    public void delete(Long id) {
        if (id == null)
            throw new ValidationException("El id no puede ser nulo para eliminar");
        if (findById(id) == null)
            throw new ResourceNotFoundException("No se encontró el registro con id: " + id);
        fdRepository.deleteById(id);
    }
}
