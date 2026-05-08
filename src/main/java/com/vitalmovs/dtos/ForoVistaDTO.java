package com.vitalmovs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ForoVistaDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Long tipoDiscapacidadId;
    private Long cantidadPublicaciones;
    private Long cantidadComentarios;
    private LocalDate ultimaActividad;
}
