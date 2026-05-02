package com.vitalmovs.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "fisioterapeuta_discapacidad")
public class FisioterapeutaDiscapacidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fisioterapeuta_id")
    private Fisioterapeuta fisioterapeuta;

    @ManyToOne
    @JoinColumn(name = "tipo_discapacidad_id")
    private TipoDiscapacidad tipoDiscapacidad;
}
