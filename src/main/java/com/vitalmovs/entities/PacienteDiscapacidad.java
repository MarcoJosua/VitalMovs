package com.vitalmovs.entities;
 
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="paciente_discapacidad")
public class PacienteDiscapacidad {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @ManyToOne
    @JoinColumn(name="tipo_discapacidad_id")
    private TipoDiscapacidad tipoDiscapacidad;
 
    @ManyToOne
    @JoinColumn(name="paciente_id")
    private Paciente paciente;
}
