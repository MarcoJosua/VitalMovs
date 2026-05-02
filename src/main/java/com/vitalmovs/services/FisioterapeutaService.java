package com.vitalmovs.services;

import com.vitalmovs.dtos.FisioterapeutaDTO;
import com.vitalmovs.entities.Fisioterapeuta;
import java.util.List;

public interface FisioterapeutaService {
    Fisioterapeuta add(Fisioterapeuta fisioterapeuta);
    FisioterapeutaDTO addDTO(FisioterapeutaDTO fisioterapeutaDTO);
    List<Fisioterapeuta> listAll();
    List<FisioterapeutaDTO> listAllDTO();
    Fisioterapeuta findById(Long id);
    Fisioterapeuta update(Fisioterapeuta fisioterapeuta);
    void delete(Long id);
}
