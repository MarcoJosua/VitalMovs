package com.vitalmovs.controllers;

import com.vitalmovs.dtos.PlanEjercicioDTO;
import com.vitalmovs.entities.PlanEjercicio;
import com.vitalmovs.services.PlanEjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/vitalmovs")
public class PlanEjercicioController {

    @Autowired
    PlanEjercicioService planEjercicioService;

    @GetMapping("/planEjercicio/plan/{planId}")
    public ResponseEntity<List<PlanEjercicioDTO>> listByPlanId(@PathVariable("planId") Long planId) {
        List<PlanEjercicioDTO> foundPlanEjercicios = planEjercicioService.listByPlanIdDTO(planId);
        return new ResponseEntity<>(foundPlanEjercicios, HttpStatus.FOUND);
    }
    @GetMapping("/planEjercicio/ejercicio/{ejercicioId}")
    public ResponseEntity<List<PlanEjercicioDTO>> listByEjercicioId(@PathVariable("ejercicioId") Long ejercicioId) {
        List<PlanEjercicioDTO> foundPlanEjercicios = planEjercicioService.listByEjercicioIdDTO(ejercicioId);
        return new ResponseEntity<>(foundPlanEjercicios, HttpStatus.FOUND);
    }
    @PostMapping("/PlanEjercicio")
    public ResponseEntity<PlanEjercicioDTO> add(@RequestBody PlanEjercicioDTO planEjercicioDTO) {
        PlanEjercicioDTO newPlanEjercicioDTO = planEjercicioService.addDTO(planEjercicioDTO);
        return new ResponseEntity<>(newPlanEjercicioDTO, HttpStatus.CREATED);
    }
    @PutMapping("/PlanEjercicio")
    public ResponseEntity<PlanEjercicio> update(@RequestBody PlanEjercicio planEjercicio) {
        PlanEjercicio updatedPlanEjercicio = planEjercicioService.update(planEjercicio);
        return new ResponseEntity<>(updatedPlanEjercicio, HttpStatus.OK);
    }
    @DeleteMapping("/PlanEjercicio/{PlanEjercicioId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("PlanEjercicioId") Long id) {
        planEjercicioService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}