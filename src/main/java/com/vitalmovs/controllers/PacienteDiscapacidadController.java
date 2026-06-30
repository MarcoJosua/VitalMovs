package com.vitalmovs.controllers;

import com.vitalmovs.dtos.PacienteDiscapacidadDTO;
import com.vitalmovs.entities.PacienteDiscapacidad;
import com.vitalmovs.repositories.PacienteDiscapacidadRepository;
import com.vitalmovs.services.PacienteDiscapacidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/vitalmovs")
public class PacienteDiscapacidadController {

    @Autowired
    PacienteDiscapacidadService pacienteDiscapacidadService;

    @Autowired
    PacienteDiscapacidadRepository pacienteDiscapacidadRepository;

    @GetMapping("/pacienteDiscapacidad/paciente/{pacienteId}")   //http://localhost:8080/vitalmovs/pacienteDiscapacidad/paciente/1
    public ResponseEntity<List<PacienteDiscapacidadDTO>> findByPacienteId(@PathVariable("pacienteId") Long id) {
        List<PacienteDiscapacidadDTO> foundPDs = pacienteDiscapacidadService.listByPacienteIdDTO(id);
        return new ResponseEntity<>(foundPDs, HttpStatus.OK);
    }

    @PostMapping("/PacienteDiscapacidad")   //http://localhost:8080/vitalmovs/PacienteDiscapacidad
    public ResponseEntity<PacienteDiscapacidadDTO> add(@RequestBody PacienteDiscapacidadDTO pacienteDiscapacidadDTO) {
        PacienteDiscapacidadDTO newPDDTO = pacienteDiscapacidadService.addDTO(pacienteDiscapacidadDTO);
        return new ResponseEntity<>(newPDDTO, HttpStatus.CREATED);
    }

    @PutMapping("/PacienteDiscapacidad")   //http://localhost:8080/vitalmovs/PacienteDiscapacidad
    public ResponseEntity<PacienteDiscapacidad> update(@RequestBody PacienteDiscapacidad pacienteDiscapacidad) {
        PacienteDiscapacidad updatedPD = pacienteDiscapacidadService.update(pacienteDiscapacidad);
        return new ResponseEntity<>(updatedPD, HttpStatus.OK);
    }

    @DeleteMapping("/PacienteDiscapacidad/{PacienteDiscapacidadId}")   //http://localhost:8080/vitalmovs/PacienteDiscapacidad/1
    public ResponseEntity<HttpStatus> delete(@PathVariable("PacienteDiscapacidadId") Long id) {
        pacienteDiscapacidadService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Method Query - buscar por pacienteId
    @GetMapping("/pacienteDiscapacidad/buscarPaciente/{pacienteId}")  //http://localhost:8080/vitalmovs/pacienteDiscapacidad/buscarPaciente/1
    public ResponseEntity<List<PacienteDiscapacidad>> findByPacienteIdMethod(@PathVariable("pacienteId") Long pacienteId) {
        List<PacienteDiscapacidad> lista = pacienteDiscapacidadRepository.findByPaciente_Id(pacienteId);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // JPQL Query - buscar por tipoDiscapacidadId
    @GetMapping("/pacienteDiscapacidad/buscarTipo/{tipoId}")  //http://localhost:8080/vitalmovs/pacienteDiscapacidad/buscarTipo/1
    public ResponseEntity<List<PacienteDiscapacidad>> findByTipoId(@PathVariable("tipoId") Long tipoId) {
        List<PacienteDiscapacidad> lista = pacienteDiscapacidadRepository.findByTipoDiscapacidadIdJPQL(tipoId);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // Native Query - contar cuantos pacientes tienen un tipo de discapacidad
    @GetMapping("/pacienteDiscapacidad/contar/{tipoId}")  //http://localhost:8080/vitalmovs/pacienteDiscapacidad/contar/1
    public ResponseEntity<Long> countByTipo(@PathVariable("tipoId") Long tipoId) {
        Long count = pacienteDiscapacidadRepository.countByTipoDiscapacidadNative(tipoId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
