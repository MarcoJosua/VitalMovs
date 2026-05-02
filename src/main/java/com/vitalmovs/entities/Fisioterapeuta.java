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
@Table(name = "fisioterapeuta")
public class Fisioterapeuta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String especialidad;

    @OneToMany(mappedBy = "fisioterapeuta", fetch = FetchType.EAGER)
    private List<FisioterapeutaDiscapacidad> fisioterapeutaDiscapacidades;
}
