package com.vitalmovs.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "asignacion")
public class Asignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //siempre para que se autogenere las ids
    private Long id;


    private String mensaje;
    private LocalDate fecha;
    private String estado;


    @OneToOne(mappedBy = "asignacion", fetch = FetchType.EAGER)
    private PlanRehabilitacion planRehabilitacion;


    @ManyToOne
    @JoinColumn(name="paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name="fisioterapeuta_id")
    private Fisioterapeuta fisioterapeuta;

}
