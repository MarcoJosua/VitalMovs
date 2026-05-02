package com.vitalmovs.repositories;

import com.vitalmovs.entities.FisioterapeutaDiscapacidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FisioterapeutaDiscapacidadRepository extends JpaRepository<FisioterapeutaDiscapacidad, Long> {
    List<FisioterapeutaDiscapacidad> findByFisioterapeuta_Id(Long fisioterapeutaId);
}
