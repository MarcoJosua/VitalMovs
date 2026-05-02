package com.vitalmovs.services;
 
import com.vitalmovs.dtos.PacienteDiscapacidadDTO;
import com.vitalmovs.entities.PacienteDiscapacidad;
 
import java.util.List;
 
public interface PacienteDiscapacidadService {
    PacienteDiscapacidad add(PacienteDiscapacidad pacienteDiscapacidad);
    PacienteDiscapacidadDTO addDTO(PacienteDiscapacidadDTO pacienteDiscapacidadDTO);
    PacienteDiscapacidad findById(Long id);
    List<PacienteDiscapacidad> listByPacienteId(Long pacienteId);
    List<PacienteDiscapacidadDTO> listByPacienteIdDTO(Long pacienteId);
    PacienteDiscapacidad update(PacienteDiscapacidad pacienteDiscapacidad);
    void delete(Long id);
}
