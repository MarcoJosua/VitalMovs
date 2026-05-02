package com.vitalmovs.services;
 
import com.vitalmovs.dtos.PacienteDTO;
import com.vitalmovs.entities.Paciente;
 
import java.util.List;
 
public interface PacienteService {
    Paciente add(Paciente paciente);
    PacienteDTO addDTO(PacienteDTO pacienteDTO);
    Paciente findById(Long id);
    List<Paciente> listAll();
    List<PacienteDTO> listAllDTO();
    Paciente update(Paciente paciente);
    void delete(Long id);
}
