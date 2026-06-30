package com.vitalmovs.controllers;

import com.vitalmovs.dtos.TipoDiscapacidadDTO;
import com.vitalmovs.entities.TipoDiscapacidad;
import com.vitalmovs.repositories.TipoDiscapacidadRepository;
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

    @Autowired
    TipoDiscapacidadRepository tipoDiscapacidadRepository;

    @GetMapping("/tipoDiscapacidad/tipos")   //http://localhost:8080/vitalmovs/tipoDiscapacidad/tipos
    public ResponseEntity<List<TipoDiscapacidadDTO>> listAll() {
        List<TipoDiscapacidadDTO> foundTipos = tipoDiscapacidadService.listAllDTO();
        return new ResponseEntity<>(foundTipos, HttpStatus.OK);
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

    // Method Query - buscar por nombre exacto
    @GetMapping("/tipoDiscapacidad/buscarNombre/{nombre}")  //http://localhost:8080/vitalmovs/tipoDiscapacidad/buscarNombre/Visual
    public ResponseEntity<List<TipoDiscapacidad>> findByNombre(@PathVariable("nombre") String nombre) {
        List<TipoDiscapacidad> tipos = tipoDiscapacidadRepository.findByNombre(nombre);
        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }

    // JPQL Query - buscar por descripcion
    @GetMapping("/tipoDiscapacidad/buscarDescripcion/{keyword}")  //http://localhost:8080/vitalmovs/tipoDiscapacidad/buscarDescripcion/vision
    public ResponseEntity<List<TipoDiscapacidad>> findByDescripcion(@PathVariable("keyword") String keyword) {
        List<TipoDiscapacidad> tipos = tipoDiscapacidadRepository.findByDescripcionContainsJPQL(keyword);
        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }

    // Native Query - buscar ignorando mayusculas/minusculas
    @GetMapping("/tipoDiscapacidad/buscarNombreExacto/{nombre}")  //http://localhost:8080/vitalmovs/tipoDiscapacidad/buscarNombreExacto/visual
    public ResponseEntity<List<TipoDiscapacidad>> findByNombreExacto(@PathVariable("nombre") String nombre) {
        List<TipoDiscapacidad> tipos = tipoDiscapacidadRepository.findByNombreIgnoreCaseNative(nombre);
        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }
}
