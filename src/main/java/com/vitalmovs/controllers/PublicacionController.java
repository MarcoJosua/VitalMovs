package com.vitalmovs.controllers;

import com.vitalmovs.services.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/vitalmovs")
public class PublicacionController {
    @Autowired
    PublicacionService publicacionService;



}
