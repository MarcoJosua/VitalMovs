package com.vitalmovs.repositories;
 
import com.vitalmovs.entities.TipoDiscapacidad;
import org.springframework.data.jpa.repository.JpaRepository;
 
import java.util.List;
 
public interface TipoDiscapacidadRepository extends JpaRepository<TipoDiscapacidad, Long> {
    List<TipoDiscapacidad> findByNombre(String nombre);


    // 1. Method Query — busca por nombre exacto
    List<TipoDiscapacidad> findByNombre(String nombre);

    // 2. JPQL Query — busca tipos cuya descripción contenga una palabra clave
    @Query("SELECT t FROM TipoDiscapacidad t WHERE t.descripcion LIKE %:keyword%")
    List<TipoDiscapacidad> findByDescripcionContainsJPQL(@Param("keyword") String keyword);

    // 3. Native Query — busca ignorando mayúsculas/minúsculas
    @Query(value = "SELECT * FROM tipo_discapacidad WHERE LOWER(nombre) = LOWER(:nombre)", nativeQuery = true)
    List<TipoDiscapacidad> findByNombreIgnoreCaseNative(@Param("nombre") String nombre);
}
