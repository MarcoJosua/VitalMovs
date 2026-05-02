package com.vitalmovs.services.impl;

import com.vitalmovs.dtos.FisioterapeutaDiscapacidadDTO;
import com.vitalmovs.entities.Fisioterapeuta;
import com.vitalmovs.entities.FisioterapeutaDiscapacidad;
import com.vitalmovs.entities.TipoDiscapacidad;
import com.vitalmovs.exceptions.ResourceNotFoundException;
import com.vitalmovs.repositories.FisioterapeutaDiscapacidadRepository;
import com.vitalmovs.services.FisioterapeutaDiscapacidadService;
import com.vitalmovs.services.FisioterapeutaService;
import com.vitalmovs.services.TipoDiscapacidadService;
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
        return fdRepository.save(fd);
    }

    @Override
    public FisioterapeutaDiscapacidadDTO addDTO(FisioterapeutaDiscapacidadDTO dto) {
        Fisioterapeuta fisioterapeuta = fisioterapeutaService.findById(dto.getFisioterapeutaId());
        TipoDiscapacidad tipoDiscapacidad = tipoDiscapacidadService.findById(dto.getTipoDiscapacidadId());
        FisioterapeutaDiscapacidad fd = new FisioterapeutaDiscapacidad(null, fisioterapeuta, tipoDiscapacidad);
        fd = add(fd);
        dto.setId(fd.getId());
        return dto;
    }

    @Override
    public FisioterapeutaDiscapacidad findById(Long id) {
        return fdRepository.findById(id).orElse(null);
    }

    @Override
    public List<FisioterapeutaDiscapacidad> listByFisioterapeutaId(Long fisioterapeutaId) {
        return fdRepository.findByFisioterapeuta_Id(fisioterapeutaId);
    }

    @Override
    public List<FisioterapeutaDiscapacidadDTO> listByFisioterapeutaIdDTO(Long fisioterapeutaId) {
        List<FisioterapeutaDiscapacidadDTO> dtoList = new ArrayList<>();
        for (FisioterapeutaDiscapacidad fd : listByFisioterapeutaId(fisioterapeutaId)) {
            dtoList.add(new FisioterapeutaDiscapacidadDTO(
                    fd.getId(),
                    fd.getFisioterapeuta().getId(),
                    fd.getTipoDiscapacidad().getId()
            ));
        }
        return dtoList;
    }

    @Override
    public FisioterapeutaDiscapacidad update(FisioterapeutaDiscapacidad fd) {
        FisioterapeutaDiscapacidad found = findById(fd.getId());
        if (found == null)
            throw new ResourceNotFoundException("No se encontró el registro con id: " + fd.getId());
        if (fd.getFisioterapeuta() != null) found.setFisioterapeuta(fd.getFisioterapeuta());
        if (fd.getTipoDiscapacidad() != null) found.setTipoDiscapacidad(fd.getTipoDiscapacidad());
        return fdRepository.save(found);
    }

    @Override
    public void delete(Long id) {
        if (findById(id) == null)
            throw new ResourceNotFoundException("No se encontró el registro con id: " + id);
        fdRepository.deleteById(id);
    }
}
