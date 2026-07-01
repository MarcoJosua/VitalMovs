package com.vitalmovs.dtos;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PacienteDiscapacidadDTO {
 
    private Long id;
    private Long tipoDiscapacidadId;
    private Long pacienteId;
    private String tipoDiscapacidadNombre;
}
