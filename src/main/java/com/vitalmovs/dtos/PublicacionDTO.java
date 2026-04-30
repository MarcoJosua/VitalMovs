package com.vitalmovs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PublicacionDTO {
    private Long id;
    private String titulo;
    private String contenido;
    private LocalDate fechaPublicacion;
    private Long foroId;
    //private Long pacienteId;
}
