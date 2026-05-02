package com.vitalmovs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComentarioDTO {
    private Long id;
    private String contenido;
    private LocalDate fechaComentario;
    private Long publicacionId;
    private Long pacienteId;
}
