package com.vitalmovs.controllers;

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
    private AsignacionService asignacionService; // Siguiendo el estilo de variable de tu equipo

    // GET: Listar todo (Equivalente a /ejercicio/ejercicios de tu compañero)
    @GetMapping("/asignacion/asignaciones")
    public ResponseEntity<List<Asignacion>> listAll() {
        List<Asignacion> foundAsignaciones = asignacionService.list();
        return new ResponseEntity<>(foundAsignaciones, HttpStatus.FOUND);
    }

    // POST: Insertar
    @PostMapping("/Asignacion")
    public ResponseEntity<Asignacion> add(@RequestBody Asignacion asignacion) {
        asignacionService.add(asignacion);
        // Retornamos HttpStatus.CREATED tal como lo hace tu equipo
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // PUT: Modificar
    @PutMapping("/Asignacion")
    public ResponseEntity<Asignacion> update(@RequestBody Asignacion asignacion) {
        asignacionService.update(asignacion);
        // Retornamos HttpStatus.OK tras actualizar
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // DELETE: Eliminar por ID
    @DeleteMapping("/Asignacion/{AsignacionId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("AsignacionId") Long id) {
        asignacionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // GET: Buscar por ID (Tus compañeros no lo mostraron en la foto, pero como tú lo tenías lo adaptamos a su estilo)
    @GetMapping("/Asignacion/{AsignacionId}")
    public ResponseEntity<Asignacion> listId(@PathVariable("AsignacionId") Long id) {
        Asignacion foundAsignacion = asignacionService.listId(id);
        return new ResponseEntity<>(foundAsignacion, HttpStatus.FOUND);
    }

}
