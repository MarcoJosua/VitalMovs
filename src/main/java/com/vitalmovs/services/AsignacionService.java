package com.vitalmovs.services;

import com.vitalmovs.dtos.AsignacionDTO;
import com.vitalmovs.dtos.ForoDTO;
import com.vitalmovs.entities.Asignacion;
import com.vitalmovs.entities.Foro;

import java.util.List;

public interface AsignacionService {

    public Asignacion add(Asignacion asignacion);

    public AsignacionDTO addDTO(AsignacionDTO asignacionDTO);

    public Asignacion findById(Long id);
    public AsignacionDTO update(Asignacion asignacion);
    public void delete(Long id);
    public List<Asignacion> listAll();
    public List<AsignacionDTO> listAllDTO();
    List<AsignacionDTO> findByFisioterapeutaId(Long fisioterapeutaId);


}
