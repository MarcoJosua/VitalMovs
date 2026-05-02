package com.vitalmovs.repositories;
 
import com.vitalmovs.entities.PacienteDiscapacidad;
import org.springframework.data.jpa.repository.JpaRepository;
 
import java.util.List;
 
public interface PacienteDiscapacidadRepository extends JpaRepository<PacienteDiscapacidad, Long> {
    List<PacienteDiscapacidad> findByPaciente_Id(Long pacienteId);
    List<PacienteDiscapacidad> findByTipoDiscapacidad_Id(Long tipoDiscapacidadId);
}
