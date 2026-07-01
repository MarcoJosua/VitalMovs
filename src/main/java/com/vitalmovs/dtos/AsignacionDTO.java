package com.vitalmovs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AsignacionDTO {
    private Long id;
    private String mensaje;
    private LocalDate fecha;
    private String estado;
    private Long pacienteId;
    private Long fisioterapeutaId;
    private Long planRehabilitacionId;
    private String nombrePaciente;
    private String nombreFisioterapeuta;
    private String apellidoPaciente;
    private String apellidoFisioterapeuta;
}
