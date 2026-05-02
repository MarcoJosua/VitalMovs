package com.vitalmovs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class HistorialDolorDTO {
    private String nombrePlan;
    private LocalDate fechaRegistro;
    private Integer nivelDolor; // Cambiado de 'int' a 'Integer' para evitar problemas con JPA
    private String observacion;



}
