package com.vitalmovs.controllers;

import com.vitalmovs.dtos.PacienteDTO;
import com.vitalmovs.entities.Paciente;
import com.vitalmovs.repositories.PacienteRepository;
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

    @Autowired
    PacienteRepository pacienteRepository;

    @GetMapping("/paciente/pacientes")
    public ResponseEntity<List<PacienteDTO>> listAll() {
        List<PacienteDTO> foundPacientes = pacienteService.listAllDTO();
        return new ResponseEntity<>(foundPacientes, HttpStatus.OK);
    }

    @GetMapping("/paciente/user/{userId}")
    public PacienteDTO findByUserId(@PathVariable Long userId) {
        return pacienteService.findByUserId(userId);
    }

    @PostMapping("/paciente")
    public ResponseEntity<PacienteDTO> add(@RequestBody PacienteDTO pacienteDTO) {
        PacienteDTO newPacienteDTO = pacienteService.addDTO(pacienteDTO);
        return new ResponseEntity<>(newPacienteDTO, HttpStatus.CREATED);
    }

    @PutMapping("/paciente")
    public ResponseEntity<Paciente> update(@RequestBody Paciente paciente) {
        Paciente updatedPaciente = pacienteService.update(paciente);
        return new ResponseEntity<>(updatedPaciente, HttpStatus.OK);
    }

    @DeleteMapping("/paciente/{PacienteId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("PacienteId") Long id) {
        pacienteService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Method Query - buscar por nombre
    @GetMapping("/paciente/buscarNombre/{nombre}")  //http://localhost:8080/vitalmovs/paciente/buscarNombre/Juan
    public ResponseEntity<List<Paciente>> findByNombre(@PathVariable("nombre") String nombre) {
        List<Paciente> pacientes = pacienteRepository.findByNombre(nombre);
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    // JPQL Query - buscar por sexo
    @GetMapping("/paciente/buscarSexo/{sexo}")  //http://localhost:8080/vitalmovs/paciente/buscarSexo/Masculino
    public ResponseEntity<List<Paciente>> findBySexo(@PathVariable("sexo") String sexo) {
        List<Paciente> pacientes = pacienteRepository.findBySexoJPQL(sexo);
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    // Native Query - buscar mayores a una edad
    @GetMapping("/paciente/buscarEdad/{edad}")  //http://localhost:8080/vitalmovs/paciente/buscarEdad/25
    public ResponseEntity<List<Paciente>> findByEdadMayor(@PathVariable("edad") Integer edad) {
        List<Paciente> pacientes = pacienteRepository.findByEdadMayorNative(edad);
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    @GetMapping("/paciente/buscarPorTipo/{tipoId}")  //http://localhost:8080/vitalmovs/paciente/buscarPorTipo/1
    public ResponseEntity<List<Paciente>> findByTipoDiscapacidad(@PathVariable("tipoId") Long tipoId) {
    List<Paciente> pacientes = pacienteRepository.findByTipoDiscapacidadJPQL(tipoId);
    return new ResponseEntity<>(pacientes, HttpStatus.OK);
}
}
