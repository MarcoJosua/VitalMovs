package com.vitalmovs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FisioterapeutaDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String especialidad;
}
