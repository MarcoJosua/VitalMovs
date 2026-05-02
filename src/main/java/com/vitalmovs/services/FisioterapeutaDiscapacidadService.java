package com.vitalmovs.services;

import com.vitalmovs.dtos.FisioterapeutaDiscapacidadDTO;
import com.vitalmovs.entities.FisioterapeutaDiscapacidad;
import java.util.List;

public interface FisioterapeutaDiscapacidadService {
    FisioterapeutaDiscapacidad add(FisioterapeutaDiscapacidad fd);
    FisioterapeutaDiscapacidadDTO addDTO(FisioterapeutaDiscapacidadDTO fdDTO);
    FisioterapeutaDiscapacidad findById(Long id);
    List<FisioterapeutaDiscapacidad> listByFisioterapeutaId(Long fisioterapeutaId);
    List<FisioterapeutaDiscapacidadDTO> listByFisioterapeutaIdDTO(Long fisioterapeutaId);
    FisioterapeutaDiscapacidad update(FisioterapeutaDiscapacidad fd);
    void delete(Long id);
}
