package com.vitalmovs.repositories;
 
import com.vitalmovs.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
 
import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
     // 1. Method Query — Spring genera el SQL por el nombre del método
    List<Paciente> findByNombre(String nombre);


    Optional<Paciente> findByUser_Id(Long userId);

    boolean existsByUser_Id(Long userid);

    // 2. JPQL Query — usa nombres de entidad y campo Java
    @Query("SELECT p FROM Paciente p WHERE p.sexo = :sexo")
    List<Paciente> findBySexoJPQL(@Param("sexo") String sexo);
 
    // 3. Native Query — SQL puro sobre la tabla real
    @Query(value = "SELECT * FROM pacientes WHERE edad > :edad", nativeQuery = true)
    List<Paciente> findByEdadMayorNative(@Param("edad") Integer edad);

    @Query("SELECT p FROM Paciente p JOIN p.pacienteDiscapacidades pd WHERE pd.tipoDiscapacidad.id = :tipoId")
    List<Paciente> findByTipoDiscapacidadJPQL(@Param("tipoId") Long tipoId);
}
