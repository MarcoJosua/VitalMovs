package com.vitalmovs.controllers;

import com.vitalmovs.dtos.AsignacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vitalmovs.entities.Asignacion;
import com.vitalmovs.services.AsignacionService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/vitalmovs")
public class AsignacionController {

    @Autowired
    AsignacionService asignacionService;

    // GET http://localhost:8080/vitalmovs/asignaciones
    @GetMapping("/asignaciones")
    public ResponseEntity<List<AsignacionDTO>> listAll() {
        List<AsignacionDTO> foundAsignaciones = asignacionService.listAllDTO();
        return new ResponseEntity<>(foundAsignaciones, HttpStatus.FOUND);
    }

    // GET http://localhost:8080/vitalmovs/asignaciones/fisioterapeuta/2
    @GetMapping("/asignaciones/fisioterapeuta/{userId}")
    public ResponseEntity<List<AsignacionDTO>> findByFisioterapeuta(@PathVariable Long userId) {
        List<AsignacionDTO> asignaciones = asignacionService.findByFisioterapeutaId(userId);
        return new ResponseEntity<>(asignaciones, HttpStatus.OK);
    }
    // GET http://localhost:8080/vitalmovs/asignaciones/paciente/2
    @GetMapping("/asignaciones/paciente/{userId}")
    public ResponseEntity<List<AsignacionDTO>> findByPaciente(@PathVariable Long userId) {
        List<AsignacionDTO> asignaciones = asignacionService.findByPacienteUserId(userId);
        return new ResponseEntity<>(asignaciones, HttpStatus.OK);
    }

    // POST http://localhost:8080/vitalmovs/asignaciones
    @PostMapping("/asignaciones")
    public ResponseEntity<AsignacionDTO> add(@RequestBody AsignacionDTO asignacionDTO) {
        AsignacionDTO newAsignacionDTO = asignacionService.addDTO(asignacionDTO);
        return new ResponseEntity<>(newAsignacionDTO, HttpStatus.CREATED);
    }

    // PUT http://localhost:8080/vitalmovs/asignaciones
    @PutMapping("/asignaciones")
    public ResponseEntity<AsignacionDTO> update(@RequestBody Asignacion asignacion) {
        AsignacionDTO updatedAsignacion = asignacionService.update(asignacion);
        return new ResponseEntity<>(updatedAsignacion, HttpStatus.OK);
    }

    // DELETE http://localhost:8080/vitalmovs/asignaciones/1
    @DeleteMapping("/asignaciones/{asignacionId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("asignacionId") Long id) {
        asignacionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
