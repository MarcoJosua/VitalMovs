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
@Table(name = "plan_Rehabilitacion")
public class PlanRehabilitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //siempre para que se autogenere las ids
    private Long id;

    private String nombre;
    private String descripcion;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private String estado;

    // RECIBE LA ID DE ASIGNACION (Es la tabla "Hija" en esta relación, tiene la FK)
    // Se usa @JoinColumn indicando el nombre de la columna en la BD
    @OneToOne
    @JoinColumn(name="asignacion_id")
    private Asignacion asignacion;



}
