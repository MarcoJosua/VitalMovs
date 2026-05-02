package com.vitalmovs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ForoDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Long tipoDiscapacidadId;
}
