package com.vitalmovs.controllers;

import com.vitalmovs.dtos.FisioterapeutaDTO;
import com.vitalmovs.entities.Fisioterapeuta;
import com.vitalmovs.repositories.FisioterapeutaRepository;
import com.vitalmovs.services.FisioterapeutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/vitalmovs")
public class FisioterapeutaController {

    @Autowired
    FisioterapeutaService fisioterapeutaService;

    @GetMapping("/fisioterapeuta/fisioterapeutas")
    // http://localhost:8080/vitalmovs/fisioterapeuta/fisioterapeutas
    public ResponseEntity<List<FisioterapeutaDTO>> listAll() {
        List<FisioterapeutaDTO> list = fisioterapeutaService.listAllDTO();
        return new ResponseEntity<>(list, HttpStatus.FOUND);
    }

    @PostMapping("/fisioterapeuta")
    // http://localhost:8080/vitalmovs/fisioterapeuta
    public ResponseEntity<FisioterapeutaDTO> add(@RequestBody FisioterapeutaDTO dto) {
        FisioterapeutaDTO newDto = fisioterapeutaService.addDTO(dto);
        return new ResponseEntity<>(newDto, HttpStatus.CREATED);
    }

    @PutMapping("/fisioterapeuta")
    // http://localhost:8080/vitalmovs/fisioterapeuta
    public ResponseEntity<Fisioterapeuta> update(@RequestBody Fisioterapeuta fisioterapeuta) {
        Fisioterapeuta updated = fisioterapeutaService.update(fisioterapeuta);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/fisioterapeuta/{fisioterapeutaId}")
    // http://localhost:8080/vitalmovs/fisioterapeuta/1
    public ResponseEntity<HttpStatus> delete(@PathVariable("fisioterapeutaId") Long id) {
        fisioterapeutaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/fisioterapeuta/buscar")
    public ResponseEntity<List<Fisioterapeuta>> buscarPorNombreOApellido(@RequestParam String texto) {
        List<Fisioterapeuta> result = fisioterapeutaService.buscarPorNombreOApellido(texto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/fisioterapeuta/especialidad")
    public ResponseEntity<List<Fisioterapeuta>> findByEspecialidad(@RequestParam String especialidad) {
        List<Fisioterapeuta> result = fisioterapeutaService.findByEspecialidad(especialidad);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/fisioterapeuta/especialidad-native")
    public ResponseEntity<List<Fisioterapeuta>> findByEspecialidadNative(@RequestParam String especialidad) {
        List<Fisioterapeuta> result = fisioterapeutaService.findByEspecialidadNative(especialidad);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
