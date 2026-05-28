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

    @GetMapping("/comentarios/{publicacionId}")   //http://localhost:8080/vitalmovs/comentarios/1  <- un id en particular
    public ResponseEntity<List<ComentarioDTO>> findByPublicacionId(@PathVariable("publicacionId") Long id) {
        List<ComentarioDTO> foundComentarios = comentarioService.listByPublicacionIdDTO(id);
        return new ResponseEntity<>(foundComentarios, HttpStatus.OK);
    }

    @PostMapping("/comentarios") //http://localhost:8080/vitalmovs/comentarios
    public ResponseEntity<ComentarioDTO> add(@RequestBody ComentarioDTO comentarioDTO){
        ComentarioDTO newComentarioDto = comentarioService.addDTO(comentarioDTO);
        return new ResponseEntity<>(newComentarioDto, HttpStatus.CREATED);
    }

    @PutMapping("/comentarios") //http://localhost:8080/vitalmovs/comentarios
    public ResponseEntity<Comentario> update(@RequestBody Comentario comentario){
        Comentario updatedComentario = comentarioService.update(comentario);
        return new ResponseEntity<>(updatedComentario, HttpStatus.OK);
    }

    @DeleteMapping("/comentarios/{comentarioId}")  //http://localhost:8080/comentarios/1
    public ResponseEntity<HttpStatus> delete(@PathVariable("comentarioId") Long id) {
        comentarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
