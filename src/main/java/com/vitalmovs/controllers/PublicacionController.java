package com.vitalmovs.controllers;

import com.vitalmovs.dtos.PublicacionDTO;
import com.vitalmovs.entities.Publicacion;
import com.vitalmovs.services.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/vitalmovs")
public class PublicacionController {
    @Autowired
    PublicacionService publicacionService;

    @GetMapping("/publicacion/foro/{foroId}")   //http://localhost:8080/vitalmovs/publicacion/foro/2  <- un id en particular
    public ResponseEntity<List<PublicacionDTO>> findByForoId(@PathVariable("foroId") Long id) {
        List<PublicacionDTO> foundPublicaciones = publicacionService.listByForoIdDTO(id);
        return new ResponseEntity<>(foundPublicaciones, HttpStatus.FOUND);
    }

    @PostMapping("/publicacion") //http://localhost:8080/vitalmovs/publicacion
    public ResponseEntity<PublicacionDTO> add(@RequestBody PublicacionDTO publicacionDTO){
        PublicacionDTO newPublicacionDto = publicacionService.addDTO(publicacionDTO);
        return new ResponseEntity<>(newPublicacionDto, HttpStatus.CREATED);
    }

    @PutMapping("/publicacion") //http://localhost:8080/vitalmovs/publicacion
    public ResponseEntity<Publicacion> update(@RequestBody Publicacion publicacion){
        Publicacion updatedPublicacion = publicacionService.update(publicacion);
        return new ResponseEntity<>(updatedPublicacion, HttpStatus.OK);
    }

    @DeleteMapping("/publicacion/{publicacionId}")  //http://localhost:8080/vitalmovs/publicacion/1
    public ResponseEntity<HttpStatus> delete(@PathVariable("publicacionId") Long id) {
        publicacionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }





}
