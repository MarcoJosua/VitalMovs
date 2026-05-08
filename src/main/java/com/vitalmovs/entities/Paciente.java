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
@Table(name="pacientes")
public class Paciente {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String nombre;
    private String apellido;
    private Integer edad;
    private String sexo;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.EAGER)
    private List<PacienteDiscapacidad> pacienteDiscapacidades;
}
