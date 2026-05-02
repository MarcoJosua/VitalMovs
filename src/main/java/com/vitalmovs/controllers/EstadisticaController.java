package com.vitalmovs.controllers;


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
    private EstadisticaService estadisticaService; // Cambiado de 'eS' para alinear con el estilo de tu grupo

    // GET: Listar todo
    @GetMapping("/estadistica/estadisticas")
    public  ResponseEntity<List<Estadistica>> listAll() {
        List<Estadistica> foundEstadisticas = estadisticaService.list();
        return new ResponseEntity<>(foundEstadisticas, HttpStatus.FOUND);
    }

    // POST: Insertar
    @PostMapping("/Estadistica")
    public ResponseEntity<Estadistica> add(@RequestBody Estadistica estadistica) {
        estadisticaService.insert(estadistica);
        // Retornamos HttpStatus.CREATED tal como lo hace tu equipo
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // PUT: Modificar
    @PutMapping("/Estadistica")
    public ResponseEntity<Estadistica> update(@RequestBody Estadistica estadistica) {
        estadisticaService.update(estadistica);
        // Retornamos HttpStatus.OK tras actualizar
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // DELETE: Eliminar por ID
    @DeleteMapping("/Estadistica/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        estadisticaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // GET: Buscar por ID
    @GetMapping("/Estadistica/{id}")
    public ResponseEntity<Estadistica> listId(@PathVariable("id") Long id) {
        Estadistica foundEstadistica = estadisticaService.listId(id);
        return new ResponseEntity<>(foundEstadistica, HttpStatus.FOUND);
    }

    // Importa el DTO arriba si no está: import pe.edu.upc.tp_oliver_web.dtos.HistorialDolorDTO;

    // GET: Query Personalizado - Historial de Dolor por Plan
    @GetMapping("/Estadistica/historial/{idPlan}")
    public ResponseEntity<List<HistorialDolorDTO>> obtenerHistorial(@PathVariable("idPlan") Long idPlan) {
        List<HistorialDolorDTO> historial = estadisticaService.obtenerHistorialPorPlan(idPlan);

        // Retornamos OK y la lista con los datos filtrados
        return new ResponseEntity<>(historial, HttpStatus.OK);
    }
}
