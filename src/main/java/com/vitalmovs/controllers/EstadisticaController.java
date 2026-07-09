package com.vitalmovs.controllers;


import com.vitalmovs.dtos.EstadisticaDTO;
import com.vitalmovs.dtos.EstadisticaGraficoDTO;
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
    @GetMapping("/estadistica")
    public ResponseEntity<List<EstadisticaDTO>> listAll() {
        List<EstadisticaDTO> foundEstadisticas = estadisticaService.listAllDTO();
        return new ResponseEntity<>(foundEstadisticas, HttpStatus.OK);
    }

    // POST http://localhost:8080/vitalmovs/estadisticas
    @PostMapping("/estadistica")
    public ResponseEntity<EstadisticaDTO> add(@RequestBody EstadisticaDTO estadisticaDTO) {
        EstadisticaDTO newEstadisticaDTO = estadisticaService.addDTO(estadisticaDTO);
        return new ResponseEntity<>(newEstadisticaDTO, HttpStatus.CREATED);
    }

    // PUT http://localhost:8080/vitalmovs/estadisticas
    @PutMapping("/estadistica")
    public ResponseEntity<EstadisticaDTO> update(@RequestBody EstadisticaDTO estadisticaDTO) {
        EstadisticaDTO updatedEstadistica = estadisticaService.update(estadisticaDTO);
        return new ResponseEntity<>(updatedEstadistica, HttpStatus.OK);
    }

    // DELETE http://localhost:8080/vitalmovs/estadisticas/1
    @DeleteMapping("/estadistica/{estadisticaId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("estadisticaId") Long id) {
        estadisticaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/estadistica/{estadisticaId}")
    public ResponseEntity<EstadisticaDTO> findById(@PathVariable Long estadisticaId) {
        EstadisticaDTO estadistica = estadisticaService.findByIdDTO(estadisticaId);
        return new ResponseEntity<>(estadistica, HttpStatus.OK);
    }

    // DASHBOARD Y ESTADISTICAd
    @GetMapping("/estadistica/planEjercicio/{planEjercicioId}")
    public ResponseEntity<java.util.List<EstadisticaDTO>> listByPlanEjercicioId(@PathVariable Long planEjercicioId) {
        java.util.List<EstadisticaDTO> estadisticas = estadisticaService.listByPlanEjercicioId(planEjercicioId);
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }

    @GetMapping("/estadistica/planRehabilitacion/{planId}")
    public ResponseEntity<java.util.List<EstadisticaDTO>> listByPlanRehabilitacionId(@PathVariable Long planId) {
        java.util.List<EstadisticaDTO> estadisticas = estadisticaService.listByPlanRehabilitacionId(planId);
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }

    @GetMapping("/estadistica/dashboard/resumen/{planId}")
    public ResponseEntity<EstadisticaGraficoDTO> resumenGeneralPorPlan(@PathVariable Long planId) {
        EstadisticaGraficoDTO resumen = estadisticaService.resumenGeneralPorPlan(planId);
        return new ResponseEntity<>(resumen, HttpStatus.OK);
    }

    @GetMapping("/estadistica/dashboard/ejercicios/{planId}")
    public ResponseEntity<java.util.List<EstadisticaGraficoDTO>> resumenPorEjercicio(@PathVariable Long planId) {
        java.util.List<EstadisticaGraficoDTO> ejercicios = estadisticaService.resumenPorEjercicio(planId);
        return new ResponseEntity<>(ejercicios, HttpStatus.OK);
    }

}
