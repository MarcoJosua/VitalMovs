package com.vitalmovs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class EstadisticaDTO {
    private Long id;
    private LocalDate fecha;
    private Integer nivelDolor;
    private Integer nivelDificultad;
    private Integer repeticionesRealizadas;
    private Integer duracionRealizada;
    private String observacion;
    private Long planEjercicioId;
}
