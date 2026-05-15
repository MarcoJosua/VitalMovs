package com.vitalmovs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToOne
    @JoinColumn(name="asignacion_id")
    private Asignacion asignacion;


    @OneToMany(mappedBy = "planRehabilitacion", fetch = FetchType.EAGER)
    private List<Estadistica> estadistica;

}
