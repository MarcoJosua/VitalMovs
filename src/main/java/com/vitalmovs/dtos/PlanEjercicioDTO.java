package com.vitalmovs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlanEjercicioDTO {

    private Long id;
    private Integer series;
    private Integer repeticiones;
    private Integer duracionRecomendada;
    private String diaSemana;
    private Integer orden;
    private Long planRehabilitacionId;
    private Long ejercicioId;
}