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
@Table(name="foros")

public class Foro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name="tipoDiscapacidad_id")
    private TipoDiscapacidad tipoDiscapacidad;

    @OneToMany(mappedBy = "foro", fetch = FetchType.EAGER)
    private List<Publicacion> publicaciones;

}
