package com.vitalmovs.controllers;

import com.vitalmovs.dtos.ForoDTO;
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

    @GetMapping("/publicaciones")   //http://localhost:8080/vitalmovs/publicaciones
    public ResponseEntity<List<PublicacionDTO>> listAll() {
        List<PublicacionDTO> foundPublicaciones = publicacionService.listAllDTO();
        return new ResponseEntity<>(foundPublicaciones, HttpStatus.OK);
    }

    @PostMapping("/publicaciones") //http://localhost:8080/vitalmovs/publicaciones
    public ResponseEntity<PublicacionDTO> add(@RequestBody PublicacionDTO publicacionDTO){
        PublicacionDTO newPublicacionDto = publicacionService.addDTO(publicacionDTO);
        return new ResponseEntity<>(newPublicacionDto, HttpStatus.CREATED);
    }

    @PutMapping("/publicaciones") //http://localhost:8080/vitalmovs/publicaciones
    public ResponseEntity<Publicacion> update(@RequestBody Publicacion publicacion){
        Publicacion updatedPublicacion = publicacionService.update(publicacion);
        return new ResponseEntity<>(updatedPublicacion, HttpStatus.OK);
    }

    @DeleteMapping("/publicaciones/{publicacionId}")  //http://localhost:8080/vitalmovs/publicaciones/1
    public ResponseEntity<HttpStatus> delete(@PathVariable("publicacionId") Long id) {
        publicacionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/publicaciones/{foroId}")   //http://localhost:8080/vitalmovs/publicaciones/1  <- un id en particular
    public ResponseEntity<List<PublicacionDTO>> listarPublicacionesPorForoFecha(@PathVariable("foroId") Long id) {
        List<PublicacionDTO> foundPublicaciones = publicacionService.listarPublicacionesPorForoFechaDTO(id);
        return new ResponseEntity<>(foundPublicaciones, HttpStatus.OK);
    }

    @GetMapping("/publicaciones/relevancia/{foroId}")     //http://localhost:8080/vitalmovs/publicaciones/relevancia/1
    public ResponseEntity<List<PublicacionDTO>> listarPublicacionesPorRelevancia(@PathVariable Long foroId) {
        List<PublicacionDTO> publicaciones =
                publicacionService.listarPublicacionesPorRelevanciaDTO(foroId);

        return new ResponseEntity<>(publicaciones, HttpStatus.OK);
    }
}
