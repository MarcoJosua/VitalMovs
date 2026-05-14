package com.vitalmovs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
import java.util.List;
 
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="tipo_discapacidad")
public class TipoDiscapacidad {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String nombre;
    private String descripcion;

    @JsonIgnore
    @OneToMany(mappedBy = "tipoDiscapacidad", fetch = FetchType.EAGER)
    private List<PacienteDiscapacidad> pacienteDiscapacidades;
}
