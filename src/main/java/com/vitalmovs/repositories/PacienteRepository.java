package com.vitalmovs.repositories;
 
import com.vitalmovs.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
 
import java.util.List;
 
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findByNombre(String nombre);
     // 1. Method Query — Spring genera el SQL por el nombre del método
    List<Paciente> findByNombre(String nombre);

    // 2. JPQL Query — usa nombres de entidad y campo Java
    @Query("SELECT p FROM Paciente p WHERE p.sexo = :sexo")
    List<Paciente> findBySexoJPQL(@Param("sexo") String sexo);
 
    // 3. Native Query — SQL puro sobre la tabla real
    @Query(value = "SELECT * FROM pacientes WHERE edad > :edad", nativeQuery = true)
    List<Paciente> findByEdadMayorNative(@Param("edad") Integer edad);
}
