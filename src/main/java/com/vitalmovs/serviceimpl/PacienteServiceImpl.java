package com.vitalmovs.serviceimpl;
 
import com.vitalmovs.dtos.PacienteDTO;
import com.vitalmovs.entities.Paciente;
import com.vitalmovs.entities.User;
import com.vitalmovs.exceptions.ResourceNotFoundException;
import com.vitalmovs.repositories.PacienteRepository;
import com.vitalmovs.services.PacienteService;
import com.vitalmovs.services.UserService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.ArrayList;
import java.util.List;
 
@Service
public class PacienteServiceImpl implements PacienteService {
 
    @Autowired
    private PacienteRepository pacienteRepository;

    //Security
    @Autowired
    private UserService userService;
 
    @Override
    public Paciente add(Paciente paciente) {
        if (paciente.getNombre().isBlank()) {
            throw new ValidationException("El nombre del paciente no puede estar en blanco");
        }
        if (paciente.getApellido().isBlank()) {
            throw new ValidationException("El apellido del paciente no puede estar en blanco");
        }
        paciente = pacienteRepository.save(paciente);
        return paciente;
    }
 
    @Override
    public PacienteDTO addDTO(PacienteDTO pacienteDTO) {
        User user = userService.findById(pacienteDTO.getUserId()); //Security
        Paciente newPaciente = new Paciente(
                null,
                pacienteDTO.getNombre(),
                pacienteDTO.getApellido(),
                pacienteDTO.getEdad(),
                pacienteDTO.getSexo(),
                user,
                null
        );
        newPaciente = add(newPaciente);
        pacienteDTO.setId(newPaciente.getId());
        return pacienteDTO;
    }
 
    @Override
    public Paciente findById(Long id) {
        return pacienteRepository.findById(id).orElse(null);
    }
 
    @Override
    public List<Paciente> listAll() {
        return pacienteRepository.findAll();
    }
 
    @Override
    public List<PacienteDTO> listAllDTO() {
        List<Paciente> pacienteList = listAll();
        List<PacienteDTO> pacienteDTOList = new ArrayList<>();
        for (Paciente p : pacienteList) {
            pacienteDTOList.add(new PacienteDTO(
                    p.getId(),
                    p.getNombre(),
                    p.getApellido(),
                    p.getEdad(),
                    p.getSexo(),
                    p.getUser().getId()
            ));
        }
        return pacienteDTOList;
    }
 
    @Override
    public Paciente update(Paciente paciente) {
        Paciente foundPaciente = findById(paciente.getId());
        if (foundPaciente == null) {
            throw new ResourceNotFoundException("No se encontro el paciente con id: " + paciente.getId().toString());
        }
        if (paciente.getNombre() != null && !paciente.getNombre().isBlank()) {
            foundPaciente.setNombre(paciente.getNombre());
        }
        if (paciente.getApellido() != null && !paciente.getApellido().isBlank()) {
            foundPaciente.setApellido(paciente.getApellido());
        }
        if (paciente.getEdad() != null) {
            foundPaciente.setEdad(paciente.getEdad());
        }
        if (paciente.getSexo() != null && !paciente.getSexo().isBlank()) {
            foundPaciente.setSexo(paciente.getSexo());
        }
        paciente = pacienteRepository.save(foundPaciente);
        return paciente;
    }
 
    @Override
    public void delete(Long id) {
        if (findById(id) == null) {
            throw new ResourceNotFoundException("No se encontro el paciente con id: " + id.toString());
        }
        pacienteRepository.deleteById(id);
    }
}
