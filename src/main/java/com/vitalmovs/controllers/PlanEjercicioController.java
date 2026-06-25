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

    //Debe haber uno listar por plan rehabilitacion

    @GetMapping("/planEjercicio")
    public ResponseEntity<List<PlanEjercicioDTO>> listAll() {
        List<PlanEjercicioDTO> foundPlanEjercicios = planEjercicioService.listAllDTO();
        return new ResponseEntity<>(foundPlanEjercicios, HttpStatus.OK);
    }

    @PostMapping("/planEjercicio")
    public ResponseEntity<PlanEjercicioDTO> add(@RequestBody PlanEjercicioDTO planEjercicioDTO) {
        PlanEjercicioDTO newPlanEjercicioDTO = planEjercicioService.addDTO(planEjercicioDTO);
        return new ResponseEntity<>(newPlanEjercicioDTO, HttpStatus.CREATED);
    }
    @PutMapping("/planEjercicio")
    public ResponseEntity<PlanEjercicio> update(@RequestBody PlanEjercicio planEjercicio) {
        PlanEjercicio updatedPlanEjercicio = planEjercicioService.update(planEjercicio);
        return new ResponseEntity<>(updatedPlanEjercicio, HttpStatus.OK);
    }
    @DeleteMapping("/planEjercicio/{planEjercicioId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("planEjercicioId") Long id) {
        planEjercicioService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/planEjercicio/planOrdenado/{planId}")
    public ResponseEntity<List<PlanEjercicioDTO>> buscarEjerciciosDePlanOrdenados(@PathVariable("planId") Long planId) {
        List<PlanEjercicioDTO> planEjercicios = planEjercicioService.buscarEjerciciosDePlanOrdenadosDTO(planId);
        return new ResponseEntity<>(planEjercicios, HttpStatus.OK);
    }



    @GetMapping("/planEjercicio/plan/{planId}")
    public ResponseEntity<List<PlanEjercicioDTO>> listByPlanId(@PathVariable("planId") Long planId) {
        List<PlanEjercicioDTO> foundPlanEjercicios = planEjercicioService.listByPlanIdDTO(planId);
        return new ResponseEntity<>(foundPlanEjercicios, HttpStatus.FOUND);
    }





    // No utiles
    @GetMapping("/planEjercicio/ejercicio/{ejercicioId}")
    public ResponseEntity<List<PlanEjercicioDTO>> listByEjercicioId(@PathVariable("ejercicioId") Long ejercicioId) {
        List<PlanEjercicioDTO> foundPlanEjercicios = planEjercicioService.listByEjercicioIdDTO(ejercicioId);
        return new ResponseEntity<>(foundPlanEjercicios, HttpStatus.FOUND);
    }
    @GetMapping("/planEjercicio/repeticiones/{repeticiones}")
    public ResponseEntity<List<PlanEjercicioDTO>> buscarPorRepeticionesMayoresOIguales(@PathVariable("repeticiones") Integer repeticiones) {
        List<PlanEjercicioDTO> planEjercicios = planEjercicioService.buscarPorRepeticionesMayoresOIgualesDTO(repeticiones);
        return new ResponseEntity<>(planEjercicios, HttpStatus.OK);
    }

    @GetMapping("/planEjercicio/orden/{orden}")
    public ResponseEntity<List<PlanEjercicioDTO>> buscarPorOrdenNative(@PathVariable("orden") Integer orden) {
        List<PlanEjercicioDTO> planEjercicios = planEjercicioService.buscarPorOrdenNativeDTO(orden);
        return new ResponseEntity<>(planEjercicios, HttpStatus.OK);
    }
    @GetMapping("/planEjercicio/dia/{dia}")
    public ResponseEntity<List<PlanEjercicioDTO>> buscarPorDiaNative(@PathVariable("dia") String dia) {
        List<PlanEjercicioDTO> planEjercicios = planEjercicioService.buscarPorDiaNativeDTO(dia);
        return new ResponseEntity<>(planEjercicios, HttpStatus.OK);
    }

}