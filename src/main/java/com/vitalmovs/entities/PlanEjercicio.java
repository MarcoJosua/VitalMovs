package com.vitalmovs.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "plan_ejercicio")
public class PlanEjercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer series;
    private Integer repeticiones;
    private Integer duracionRecomendada;
    private String diaSemana;
    private Integer orden;

    @ManyToOne
    @JoinColumn(name = "plan_Rehabilitacion_id")
    private PlanRehabilitacion planRehabilitacion;

    @ManyToOne
    @JoinColumn(name = "ejercicio_id")
    private Ejercicio ejercicio;

    // ENVÍA SU ID A ESTADISTICA (Es la tabla "Padre" en esta relación, tiene la PK)
    // Se usa mappedBy, FetchType.EAGER y una Lista según el ejemplo de tu clase
    @OneToMany(mappedBy = "planEjercicio", fetch = FetchType.EAGER)
    private List<Estadistica> estadistica;
}