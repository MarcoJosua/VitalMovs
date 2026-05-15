package com.vitalmovs.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "estadistica")

public class Estadistica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //siempre para que se autogenere las ids
    private Long id;
    private LocalDate fecha;
    private int nivelDolor;
    private String observacion;

    // RECIBE LA ID DE PLAN_REHABILITACION (Es la tabla "Hija", tiene la FK)
    // Se usa @JoinColumn indicando el nombre exacto de la columna en la BD
    @ManyToOne
    @JoinColumn(name="planRehabilitacion_id")
    private PlanRehabilitacion planRehabilitacion;

}
