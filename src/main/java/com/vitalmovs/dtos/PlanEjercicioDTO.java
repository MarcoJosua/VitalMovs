package com.vitalmovs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlanEjercicioDTO {

    private Long id;
    private Integer repeticiones;
    private String dias;
    private Integer orden;
    private Long planId;
    private Long ejercicioId;
}