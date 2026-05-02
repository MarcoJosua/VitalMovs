package com.vitalmovs.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="publicaciones")
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String contenido;
    private LocalDate fechaPublicacion;

    @ManyToOne
    @JoinColumn(name="foro_id")
    private Foro foro;

    @ManyToOne
    @JoinColumn(name="paciente_id")
    private Paciente paciente;

    @OneToMany(mappedBy = "publicacion", fetch = FetchType.EAGER)
    private List<Comentario> comentarios;
}
