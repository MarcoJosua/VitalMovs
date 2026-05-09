package com.vitalmovs.repositories;
 
import com.vitalmovs.entities.PacienteDiscapacidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
 
import java.util.List;
 
public interface PacienteDiscapacidadRepository extends JpaRepository<PacienteDiscapacidad, Long> {

    // 1. Method Query — todas las discapacidades de un paciente
    List<PacienteDiscapacidad> findByPaciente_Id(Long pacienteId);

    // 2. JPQL Query — busca por id del tipo de discapacidad
    @Query("SELECT pd FROM PacienteDiscapacidad pd WHERE pd.tipoDiscapacidad.id = :tipoId")
    List<PacienteDiscapacidad> findByTipoDiscapacidadIdJPQL(@Param("tipoId") Long tipoId);

    // 3. Native Query — cuenta cuántos pacientes tienen un tipo de discapacidad
    @Query(value = "SELECT COUNT(*) FROM paciente_discapacidad WHERE tipo_discapacidad_id = :tipoId", nativeQuery = true)
    Long countByTipoDiscapacidadNative(@Param("tipoId") Long tipoId);
}
