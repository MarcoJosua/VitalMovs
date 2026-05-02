package com.vitalmovs.controllers;
 
import com.vitalmovs.dtos.TipoDiscapacidadDTO;
import com.vitalmovs.entities.TipoDiscapacidad;
import com.vitalmovs.services.TipoDiscapacidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@CrossOrigin("*")
@RequestMapping("/vitalmovs")
public class TipoDiscapacidadController {
 
    @Autowired
    TipoDiscapacidadService tipoDiscapacidadService;
 
    @GetMapping("/tipoDiscapacidad/tipos")   //http://localhost:8080/vitalmovs/tipoDiscapacidad/tipos
    public ResponseEntity<List<TipoDiscapacidadDTO>> listAll() {
        List<TipoDiscapacidadDTO> foundTipos = tipoDiscapacidadService.listAllDTO();
        return new ResponseEntity<>(foundTipos, HttpStatus.FOUND);
    }
 
    @PostMapping("/TipoDiscapacidad")   //http://localhost:8080/vitalmovs/TipoDiscapacidad
    public ResponseEntity<TipoDiscapacidadDTO> add(@RequestBody TipoDiscapacidadDTO tipoDiscapacidadDTO) {
        TipoDiscapacidadDTO newTipoDTO = tipoDiscapacidadService.addDTO(tipoDiscapacidadDTO);
        return new ResponseEntity<>(newTipoDTO, HttpStatus.CREATED);
    }
 
    @PutMapping("/TipoDiscapacidad")   //http://localhost:8080/vitalmovs/TipoDiscapacidad
    public ResponseEntity<TipoDiscapacidad> update(@RequestBody TipoDiscapacidad tipoDiscapacidad) {
        TipoDiscapacidad updatedTipo = tipoDiscapacidadService.update(tipoDiscapacidad);
        return new ResponseEntity<>(updatedTipo, HttpStatus.OK);
    }
 
    @DeleteMapping("/TipoDiscapacidad/{TipoDiscapacidadId}")   //http://localhost:8080/vitalmovs/TipoDiscapacidad/1
    public ResponseEntity<HttpStatus> delete(@PathVariable("TipoDiscapacidadId") Long id) {
        tipoDiscapacidadService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
