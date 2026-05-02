package com.vitalmovs.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="comentarios")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenido;
    private LocalDate fechaComentario;

    @ManyToOne
    @JoinColumn(name="publicacion_id")
    private Publicacion publicacion;

    @ManyToOne
    @JoinColumn(name="paciente_id")
    private Paciente paciente;

}
