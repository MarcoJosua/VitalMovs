package com.vitalmovs.dtos;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PacienteDTO {
 
    private Long id;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String sexo;
}
