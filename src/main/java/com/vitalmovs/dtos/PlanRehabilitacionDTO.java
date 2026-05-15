package com.vitalmovs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PlanRehabilitacionDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fecha_inicio;

    private Long asignacionId;

}
