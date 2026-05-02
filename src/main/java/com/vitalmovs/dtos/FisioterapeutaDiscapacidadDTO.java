package com.vitalmovs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FisioterapeutaDiscapacidadDTO {
    private Long id;
    private Long fisioterapeutaId;
    private Long tipoDiscapacidadId;
}
