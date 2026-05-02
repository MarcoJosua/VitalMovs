package com.vitalmovs.controllers;
 
import com.vitalmovs.dtos.PacienteDTO;
import com.vitalmovs.entities.Paciente;
import com.vitalmovs.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@CrossOrigin("*")
@RequestMapping("/vitalmovs")
public class PacienteController {
 
    @Autowired
    PacienteService pacienteService;
 
    @GetMapping("/paciente/pacientes")   //http://localhost:8080/vitalmovs/paciente/pacientes
    public ResponseEntity<List<PacienteDTO>> listAll() {
        List<PacienteDTO> foundPacientes = pacienteService.listAllDTO();
        return new ResponseEntity<>(foundPacientes, HttpStatus.FOUND);
    }
 
    @PostMapping("/Paciente")   //http://localhost:8080/vitalmovs/Paciente
    public ResponseEntity<PacienteDTO> add(@RequestBody PacienteDTO pacienteDTO) {
        PacienteDTO newPacienteDTO = pacienteService.addDTO(pacienteDTO);
        return new ResponseEntity<>(newPacienteDTO, HttpStatus.CREATED);
    }
 
    @PutMapping("/Paciente")   //http://localhost:8080/vitalmovs/Paciente
    public ResponseEntity<Paciente> update(@RequestBody Paciente paciente) {
        Paciente updatedPaciente = pacienteService.update(paciente);
        return new ResponseEntity<>(updatedPaciente, HttpStatus.OK);
    }
 
    @DeleteMapping("/Paciente/{PacienteId}")   //http://localhost:8080/vitalmovs/Paciente/1
    public ResponseEntity<HttpStatus> delete(@PathVariable("PacienteId") Long id) {
        pacienteService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
