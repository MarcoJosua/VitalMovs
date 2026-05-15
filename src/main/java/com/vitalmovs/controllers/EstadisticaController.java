package com.vitalmovs.controllers;


import com.vitalmovs.dtos.EstadisticaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vitalmovs.dtos.HistorialDolorDTO;
import com.vitalmovs.entities.Estadistica;
import com.vitalmovs.services.EstadisticaService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/vitalmovs")

public class EstadisticaController {

    @Autowired
    EstadisticaService estadisticaService;

    // GET http://localhost:8080/vitalmovs/estadisticas
    @GetMapping("/estadisticas")
    public ResponseEntity<List<EstadisticaDTO>> listAll() {
        List<EstadisticaDTO> foundEstadisticas = estadisticaService.listAllDTO();
        return new ResponseEntity<>(foundEstadisticas, HttpStatus.FOUND);
    }

    // POST http://localhost:8080/vitalmovs/estadisticas
    @PostMapping("/estadisticas")
    public ResponseEntity<EstadisticaDTO> add(@RequestBody EstadisticaDTO estadisticaDTO) {
        EstadisticaDTO newEstadisticaDTO = estadisticaService.addDTO(estadisticaDTO);
        return new ResponseEntity<>(newEstadisticaDTO, HttpStatus.CREATED);
    }

    // PUT http://localhost:8080/vitalmovs/estadisticas
    @PutMapping("/estadisticas")
    public ResponseEntity<EstadisticaDTO> update(@RequestBody EstadisticaDTO estadisticaDTO) {
        EstadisticaDTO updatedEstadistica = estadisticaService.update(estadisticaDTO);
        return new ResponseEntity<>(updatedEstadistica, HttpStatus.OK);
    }

    // DELETE http://localhost:8080/vitalmovs/estadisticas/1
    @DeleteMapping("/estadisticas/{estadisticaId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("estadisticaId") Long id) {
        estadisticaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // GET http://localhost:8080/vitalmovs/estadisticas/1
    @GetMapping("/estadisticas/{estadisticaId}")
    public ResponseEntity<EstadisticaDTO> findById(@PathVariable("estadisticaId") Long id) {
        EstadisticaDTO foundEstadistica = estadisticaService.findById(id);
        return new ResponseEntity<>(foundEstadistica, HttpStatus.FOUND);
    }

    // GET http://localhost:8080/vitalmovs/estadisticas/plan/1
    @GetMapping("/estadisticas/plan/{planId}")
    public ResponseEntity<List<Estadistica>> findByPlan(@PathVariable Long planId) {
        List<Estadistica> estadisticas = estadisticaService.findByPlanNative(planId);
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }

}
