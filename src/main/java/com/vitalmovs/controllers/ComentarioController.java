package com.vitalmovs.controllers;

import com.vitalmovs.services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/vitalmovs")
public class ComentarioController {
    @Autowired
    ComentarioService comentarioService;

}
