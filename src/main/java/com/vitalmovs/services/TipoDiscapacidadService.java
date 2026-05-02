package com.vitalmovs.services;
 
import com.vitalmovs.dtos.TipoDiscapacidadDTO;
import com.vitalmovs.entities.TipoDiscapacidad;
 
import java.util.List;
 
public interface TipoDiscapacidadService {
    TipoDiscapacidad add(TipoDiscapacidad tipoDiscapacidad);
    TipoDiscapacidadDTO addDTO(TipoDiscapacidadDTO tipoDiscapacidadDTO);
    TipoDiscapacidad findById(Long id);
    List<TipoDiscapacidad> listAll();
    List<TipoDiscapacidadDTO> listAllDTO();
    TipoDiscapacidad update(TipoDiscapacidad tipoDiscapacidad);
    void delete(Long id);
}
