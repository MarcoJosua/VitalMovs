package com.vitalmovs.controllers;


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
    private PlanRehabilitacionService planRehabilitacionService; // Cambiado de 'pS' para alinear con el estilo de tu grupo

    // GET: Listar todo
    @GetMapping("/planRehabilitacion/planes")
    public ResponseEntity<List<PlanRehabilitacion>> listAll() {
        List<PlanRehabilitacion> foundPlanes = planRehabilitacionService.list();
        return new ResponseEntity<>(foundPlanes, HttpStatus.FOUND);
    }

    // POST: Insertar
    @PostMapping("/PlanRehabilitacion")
    public ResponseEntity<PlanRehabilitacion> add(@RequestBody PlanRehabilitacion planRehabilitacion) {
        planRehabilitacionService.add(planRehabilitacion);
        // Retornamos HttpStatus.CREATED tal como lo hace tu equipo
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // PUT: Modificar
    @PutMapping("/PlanRehabilitacion")
    public ResponseEntity<PlanRehabilitacion> update(@RequestBody PlanRehabilitacion planRehabilitacion) {
        planRehabilitacionService.update(planRehabilitacion);
        // Retornamos HttpStatus.OK tras actualizar
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // DELETE: Eliminar por ID
    @DeleteMapping("/PlanRehabilitacion/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        planRehabilitacionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // GET: Buscar por ID
    @GetMapping("/PlanRehabilitacion/{id}")
    public ResponseEntity<PlanRehabilitacion> listId(@PathVariable("id") Long id) {
        PlanRehabilitacion foundPlan = planRehabilitacionService.listId(id);
        return new ResponseEntity<>(foundPlan, HttpStatus.FOUND);
    }

}
