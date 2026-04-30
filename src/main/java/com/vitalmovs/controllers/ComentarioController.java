package com.vitalmovs.controllers;

import com.vitalmovs.dtos.ComentarioDTO;
import com.vitalmovs.entities.Comentario;
import com.vitalmovs.services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/vitalmovs")
public class ComentarioController {
    @Autowired
    ComentarioService comentarioService;

    @GetMapping("/comentario/publicacion/{publicacionId}")   //http://localhost:8080/vitalmovs/comentario/publicacion/2  <- un id en particular
    public ResponseEntity<List<ComentarioDTO>> findByPublicacionId(@PathVariable("publicacionId") Long id) {
        List<ComentarioDTO> foundComentarios = comentarioService.listByPublicacionIdDTO(id);
        return new ResponseEntity<>(foundComentarios, HttpStatus.FOUND);
    }

    @PostMapping("/Comentario") //http://localhost:8080/vitalmovs/comentario
    public ResponseEntity<ComentarioDTO> add(@RequestBody ComentarioDTO comentarioDTO){
        ComentarioDTO newComentarioDto = comentarioService.addDTO(comentarioDTO);
        return new ResponseEntity<>(newComentarioDto, HttpStatus.CREATED);
    }

    @PutMapping("/Comentario") //http://localhost:8080/vitalmovs/comentario
    public ResponseEntity<Comentario> update(@RequestBody Comentario comentario){
        Comentario updatedComentario = comentarioService.update(comentario);
        return new ResponseEntity<>(updatedComentario, HttpStatus.OK);
    }

    @DeleteMapping("/Comentario/{ComentarioId}")  //http://localhost:8080/vitalmovs/comentario/1
    public ResponseEntity<HttpStatus> delete(@PathVariable("ComentarioId") Long id) {
        comentarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
