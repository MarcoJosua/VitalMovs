package com.vitalmovs.repositories;

import com.vitalmovs.entities.Fisioterapeuta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FisioterapeutaRepository extends JpaRepository<Fisioterapeuta, Long> {
    // 1. METHOD QUERY — Spring genera la query automáticamente por el nombre del método
    // Busca fisioterapeutas por especialidad (ignorando mayúsculas/minúsculas)
    List<Fisioterapeuta> findByEspecialidadIgnoreCase(String especialidad);

    // 2. JPQL QUERY — Query en lenguaje de objetos Java (usa nombres de entidad y atributos)
    // Busca fisioterapeutas cuyo nombre o apellido contenga el texto buscado
    @Query("SELECT f FROM Fisioterapeuta f WHERE LOWER(f.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) OR LOWER(f.apellido) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Fisioterapeuta> buscarPorNombreOApellido(@Param("texto") String texto);

    // 3. NATIVE QUERY — Query en SQL puro (usa nombres reales de tabla y columnas de la BD)
    // Busca todos los fisioterapeutas de una especialidad específica directamente en la tabla
    @Query(value = "SELECT * FROM fisioterapeuta WHERE especialidad = :especialidad", nativeQuery = true)
    List<Fisioterapeuta> findByEspecialidadNative(@Param("especialidad") String especialidad);
  
}
