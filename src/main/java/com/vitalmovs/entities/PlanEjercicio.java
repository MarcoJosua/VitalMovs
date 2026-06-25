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

    @OneToMany( mappedBy = "planEjercicio", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Estadistica> estadistica;
}