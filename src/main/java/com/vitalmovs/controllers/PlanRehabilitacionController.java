package com.vitalmovs.controllers;


import com.vitalmovs.dtos.PlanRehabilitacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vitalmovs.entities.PlanRehabilitacion;
import com.vitalmovs.services.PlanRehabilitacionService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/vitalmovs")
public class PlanRehabilitacionController {

    @Autowired
    PlanRehabilitacionService planRehabilitacionService;

    // GET http://localhost:8080/vitalmovs/planes
    @GetMapping("/planes")
    public ResponseEntity<List<PlanRehabilitacionDTO>> listAll() {
        List<PlanRehabilitacionDTO> foundPlanes = planRehabilitacionService.listAllDTO();
        return new ResponseEntity<>(foundPlanes, HttpStatus.FOUND);
    }

    // POST http://localhost:8080/vitalmovs/planes
    @PostMapping("/planes")
    public ResponseEntity<PlanRehabilitacionDTO> add(@RequestBody PlanRehabilitacionDTO planRehabilitacionDTO) {
        PlanRehabilitacionDTO newPlanDTO = planRehabilitacionService.addDTO(planRehabilitacionDTO);
        return new ResponseEntity<>(newPlanDTO, HttpStatus.CREATED);
    }

    // PUT http://localhost:8080/vitalmovs/planes
    @PutMapping("/planes")
    public ResponseEntity<PlanRehabilitacionDTO> update(@RequestBody PlanRehabilitacionDTO planRehabilitacionDTO) {
        PlanRehabilitacionDTO updatedPlan = planRehabilitacionService.update(planRehabilitacionDTO);
        return new ResponseEntity<>(updatedPlan, HttpStatus.OK);
    }

    // DELETE http://localhost:8080/vitalmovs/planes/1
    @DeleteMapping("/planes/{planId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("planId") Long id) {
        planRehabilitacionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // GET http://localhost:8080/vitalmovs/planes/1
    @GetMapping("/planes/{planId}")
    public ResponseEntity<PlanRehabilitacionDTO> findById(@PathVariable("planId") Long id) {
        PlanRehabilitacionDTO foundPlan = planRehabilitacionService.findById(id);
        return new ResponseEntity<>(foundPlan, HttpStatus.FOUND);
    }

}
