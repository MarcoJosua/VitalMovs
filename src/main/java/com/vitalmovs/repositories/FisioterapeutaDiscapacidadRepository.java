package com.vitalmovs.repositories;

import com.vitalmovs.entities.FisioterapeutaDiscapacidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FisioterapeutaDiscapacidadRepository extends JpaRepository<FisioterapeutaDiscapacidad, Long> {
    // 1. METHOD QUERY — Busca todos los registros de un fisioterapeuta por su ID
    List<FisioterapeutaDiscapacidad> findByFisioterapeuta_Id(Long fisioterapeutaId);

    // 2. JPQL QUERY — Busca los registros filtrando por el ID del tipo de discapacidad
    @Query("SELECT fd FROM FisioterapeutaDiscapacidad fd WHERE fd.tipoDiscapacidad.id = :tipoDiscapacidadId")
    List<FisioterapeutaDiscapacidad> findByTipoDiscapacidadId(@Param("tipoDiscapacidadId") Long tipoDiscapacidadId);

    // 3. NATIVE QUERY — Busca registros cruzando fisioterapeuta y tipo de discapacidad por sus IDs
    @Query(value = "SELECT * FROM fisioterapeuta_discapacidad WHERE fisioterapeuta_id = :fisioterapeutaId AND tipo_discapacidad_id = :tipoDiscapacidadId", nativeQuery = true)
    List<FisioterapeutaDiscapacidad> findByFisioterapeutaAndTipoNative(@Param("fisioterapeutaId") Long fisioterapeutaId, @Param("tipoDiscapacidadId") Long tipoDiscapacidadId);
}
