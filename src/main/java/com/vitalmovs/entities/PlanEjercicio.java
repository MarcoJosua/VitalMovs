package com.vitalmovs.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "plan_ejercicio")
public class PlanEjercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer repeticiones;
    private String dias;
    private Integer orden;

    @ManyToOne
    @JoinColumn(name = "plan_Rehabilitacion_id")
    private PlanRehabilitacion planRehabilitacion;

    @ManyToOne
    @JoinColumn(name = "ejercicio_id")
    private Ejercicio ejercicio;
}