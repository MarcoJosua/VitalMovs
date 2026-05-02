package com.vitalmovs.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "planRehabilitacion")
public class PlanRehabilitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //siempre para que se autogenere las ids
    private Long id;

    private String nombre;
    private String descripcion;
    private LocalDate fecha_inicio;

    // RECIBE LA ID DE ASIGNACION (Es la tabla "Hija" en esta relación, tiene la FK)
    // Se usa @JoinColumn indicando el nombre de la columna en la BD
    @OneToOne
    @JoinColumn(name="asignacion_id")
    private Asignacion asignacion;

    // ENVÍA SU ID A ESTADISTICA (Es la tabla "Padre" en esta relación, tiene la PK)
    // Se usa mappedBy, FetchType.EAGER y una Lista según el ejemplo de tu clase
    @OneToMany(mappedBy = "planRehabilitacion", fetch = FetchType.EAGER)
    private List<Estadistica> estadistica;

}
