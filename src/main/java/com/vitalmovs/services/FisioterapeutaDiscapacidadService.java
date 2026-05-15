package com.vitalmovs.services;

import com.vitalmovs.dtos.FisioterapeutaDiscapacidadDTO;
import com.vitalmovs.entities.FisioterapeutaDiscapacidad;
import java.util.List;

public interface FisioterapeutaDiscapacidadService {
    FisioterapeutaDiscapacidad add(FisioterapeutaDiscapacidad fd);
    FisioterapeutaDiscapacidadDTO addDTO(FisioterapeutaDiscapacidadDTO fdDTO);
    FisioterapeutaDiscapacidad findById(Long id);
    FisioterapeutaDiscapacidad update(FisioterapeutaDiscapacidad fd);
    void delete(Long id);

    // Query Method
    List<FisioterapeutaDiscapacidad> listByFisioterapeutaId(Long fisioterapeutaId);
    List<FisioterapeutaDiscapacidadDTO> listByFisioterapeutaIdDTO(Long fisioterapeutaId);

    // JPQL
    List<FisioterapeutaDiscapacidadDTO> listByTipoDiscapacidadIdDTO(Long tipoDiscapacidadId);

    // Native Query — este es el que te falta
    List<FisioterapeutaDiscapacidad> findByFisioterapeutaAndTipoNative(Long fisioterapeutaId, Long tipoDiscapacidadId);
}
