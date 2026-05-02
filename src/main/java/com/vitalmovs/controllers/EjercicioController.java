package com.vitalmovs.controllers;

import com.vitalmovs.dtos.EjercicioDTO;
import com.vitalmovs.entities.Ejercicio;
import com.vitalmovs.services.EjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/vitalmovs")
public class EjercicioController {

    @Autowired
    EjercicioService ejercicioService;

    @GetMapping("/ejercicio/ejercicios")
    public ResponseEntity<List<EjercicioDTO>> listAll() {
        List<EjercicioDTO> foundEjercicios = ejercicioService.listAllDTO();
        return new ResponseEntity<>(foundEjercicios, HttpStatus.FOUND);
    }
    @PostMapping("/Ejercicio")
    public ResponseEntity<EjercicioDTO> add(@RequestBody EjercicioDTO ejercicioDTO) {
        EjercicioDTO newEjercicioDTO = ejercicioService.addDTO(ejercicioDTO);
        return new ResponseEntity<>(newEjercicioDTO, HttpStatus.CREATED);
    }
    @PutMapping("/Ejercicio")
    public ResponseEntity<Ejercicio> update(@RequestBody Ejercicio ejercicio) {
        Ejercicio updatedEjercicio = ejercicioService.update(ejercicio);
        return new ResponseEntity<>(updatedEjercicio, HttpStatus.OK);
    }
    @DeleteMapping("/Ejercicio/{EjercicioId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("EjercicioId") Long id) {
        ejercicioService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}