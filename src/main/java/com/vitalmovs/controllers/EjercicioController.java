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

    @GetMapping("/ejercicio")
    public ResponseEntity<List<EjercicioDTO>> listAll() {
        List<EjercicioDTO> foundEjercicios = ejercicioService.listAllDTO();
        return new ResponseEntity<>(foundEjercicios, HttpStatus.OK);
    }

    @GetMapping("/ejercicio/buscar/{texto}")
    public ResponseEntity<List<EjercicioDTO>> buscarPorNombreODescripcion(@PathVariable("texto") String texto) {
        List<EjercicioDTO> ejercicios = ejercicioService.buscarPorNombreODescripcionDTO(texto);
        return new ResponseEntity<>(ejercicios, HttpStatus.OK);
    }

    @GetMapping("/ejercicio/buscarNative/{nombre}")
    public ResponseEntity<List<EjercicioDTO>> buscarPorNombreNative(@PathVariable("nombre") String nombre) {
        List<EjercicioDTO> ejercicios = ejercicioService.buscarPorNombreNativeDTO(nombre);
        return new ResponseEntity<>(ejercicios, HttpStatus.OK);
    }

    @PostMapping("/ejercicio")
    public ResponseEntity<EjercicioDTO> add(@RequestBody EjercicioDTO ejercicioDTO) {
        EjercicioDTO newEjercicioDTO = ejercicioService.addDTO(ejercicioDTO);
        return new ResponseEntity<>(newEjercicioDTO, HttpStatus.CREATED);
    }

    @PutMapping("/ejercicio")
    public ResponseEntity<EjercicioDTO> update(@RequestBody EjercicioDTO ejercicio) {
        EjercicioDTO updatedEjercicio = ejercicioService.update(ejercicio);
        return new ResponseEntity<>(updatedEjercicio, HttpStatus.OK);
    }

    @DeleteMapping("/ejercicio/{EjercicioId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("EjercicioId") Long id) {
        ejercicioService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}