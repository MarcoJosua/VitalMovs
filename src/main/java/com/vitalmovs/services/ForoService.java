package com.vitalmovs.services;

import com.vitalmovs.dtos.ForoDTO;
import com.vitalmovs.dtos.ForoVistaDTO;
import com.vitalmovs.entities.Foro;

import java.util.List;

public interface ForoService {
    Foro add(Foro foro);
    ForoDTO addDTO(ForoDTO foroDTO);
    List<Foro> listAll();
    List<ForoDTO> listAllDTO();
    Foro findById(Long id);
    Foro update(Foro foro);
    void delete(Long id);
    List<ForoVistaDTO> listarForosVisiblesConResumenPorPaciente(Long pacienteId);

}
