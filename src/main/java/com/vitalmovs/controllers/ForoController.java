package com.vitalmovs.controllers;

import com.vitalmovs.dtos.ForoDTO;
import com.vitalmovs.entities.Foro;
import com.vitalmovs.services.ForoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/vitalmovs")
public class ForoController {
    @Autowired
    ForoService foroService;

    @GetMapping("/foro/foros")   //http://localhost:8080/vitalmovs/foros  <- un id en particular
    public ResponseEntity<List<ForoDTO>> listAll() {
        List<ForoDTO> foundForos = foroService.listAllDTO();
        return new ResponseEntity<>(foundForos, HttpStatus.FOUND);
    }

    @PostMapping("/Foro") //http://localhost:8080/vitalmovs/foro
    public ResponseEntity<ForoDTO> add(@RequestBody ForoDTO foroDTO){
        ForoDTO newForoDto = foroService.addDTO(foroDTO);
        return new ResponseEntity<>(newForoDto, HttpStatus.CREATED);
    }

    @PutMapping("/Foro") //http://localhost:8080/vitalmovs/foro
    public ResponseEntity<Foro> update(@RequestBody Foro foro){
        Foro updatedForo = foroService.update(foro);
        return new ResponseEntity<>(updatedForo, HttpStatus.OK);
    }

    @DeleteMapping("/Foro/{ForoId}")  //http://localhost:8080/vitalmovs/foro/1
    public ResponseEntity<HttpStatus> delete(@PathVariable("ForoId") Long id) {
        foroService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    
    
}
