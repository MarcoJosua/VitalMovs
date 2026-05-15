package com.vitalmovs.controllers;

import com.vitalmovs.dtos.FisioterapeutaDiscapacidadDTO;
import com.vitalmovs.entities.FisioterapeutaDiscapacidad;
import com.vitalmovs.services.FisioterapeutaDiscapacidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/vitalmovs")
public class FisioterapeutaDiscapacidadController {

    @Autowired
    FisioterapeutaDiscapacidadService fdService;

    @GetMapping("/fisioterapeutaDiscapacidad/fisioterapeuta/{fisioterapeutaId}")
    // http://localhost:8080/vitalmovs/fisioterapeutaDiscapacidad/fisioterapeuta/1
    public ResponseEntity<List<FisioterapeutaDiscapacidadDTO>> findByFisioterapeutaId(
            @PathVariable("fisioterapeutaId") Long id) {
        List<FisioterapeutaDiscapacidadDTO> list = fdService.listByFisioterapeutaIdDTO(id);
        return new ResponseEntity<>(list, HttpStatus.FOUND);
    }

    @PostMapping("/fisioterapeutaDiscapacidad")
    // http://localhost:8080/vitalmovs/fisioterapeutaDiscapacidad
    public ResponseEntity<FisioterapeutaDiscapacidadDTO> add(@RequestBody FisioterapeutaDiscapacidadDTO dto) {
        FisioterapeutaDiscapacidadDTO newDto = fdService.addDTO(dto);
        return new ResponseEntity<>(newDto, HttpStatus.CREATED);
    }

    @PutMapping("/fisioterapeutaDiscapacidad")
    // http://localhost:8080/vitalmovs/fisioterapeutaDiscapacidad
    public ResponseEntity<FisioterapeutaDiscapacidad> update(@RequestBody FisioterapeutaDiscapacidad fd) {
        FisioterapeutaDiscapacidad updated = fdService.update(fd);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/fisioterapeutaDiscapacidad/{fdId}")
    // http://localhost:8080/vitalmovs/fisioterapeutaDiscapacidad/1
    public ResponseEntity<HttpStatus> delete(@PathVariable("fdId") Long id) {
        fdService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/fisioterapeutaDiscapacidad/tipo/{tipoDiscapacidadId}")
    public ResponseEntity<List<FisioterapeutaDiscapacidadDTO>> findByTipoDiscapacidad(
            @PathVariable("tipoDiscapacidadId") Long tipoDiscapacidadId) {
        // ✅ BIEN — usas la instancia inyectada fdService
        List<FisioterapeutaDiscapacidadDTO> list = fdService.listByTipoDiscapacidadIdDTO(tipoDiscapacidadId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/fisioterapeutaDiscapacidad/native")
    public ResponseEntity<List<FisioterapeutaDiscapacidad>> findByFisioterapeutaAndTipoNative(
            @RequestParam Long fisioterapeutaId,
            @RequestParam Long tipoDiscapacidadId) {
        List<FisioterapeutaDiscapacidad> list = fdService.findByFisioterapeutaAndTipoNative(fisioterapeutaId, tipoDiscapacidadId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
