package com.vitalmovs.repositories;

import com.vitalmovs.entities.FisioterapeutaDiscapacidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FisioterapeutaDiscapacidadRepository extends JpaRepository<FisioterapeutaDiscapacidad, Long> {

    // 1. METHOD QUERY
    List<FisioterapeutaDiscapacidad> findByFisioterapeuta_Id(Long fisioterapeutaId);

    // 2. JPQL QUERY
    @Query("SELECT fd FROM FisioterapeutaDiscapacidad fd WHERE fd.tipoDiscapacidad.id = :tipoDiscapacidadId")
    List<FisioterapeutaDiscapacidad> findByTipoDiscapacidadId(@Param("tipoDiscapacidadId") Long tipoDiscapacidadId);

    // 3. NATIVE QUERY
    @Query(value = "SELECT * FROM fisioterapeuta_discapacidad WHERE fisioterapeuta_id = :fisioterapeutaId AND tipo_discapacidad_id = :tipoDiscapacidadId", nativeQuery = true)
    List<FisioterapeutaDiscapacidad> findByFisioterapeutaAndTipoNative(
            @Param("fisioterapeutaId") Long fisioterapeutaId,
            @Param("tipoDiscapacidadId") Long tipoDiscapacidadId);
}
