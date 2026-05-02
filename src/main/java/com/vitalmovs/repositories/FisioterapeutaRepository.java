package com.vitalmovs.repositories;

import com.vitalmovs.entities.Fisioterapeuta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FisioterapeutaRepository extends JpaRepository<Fisioterapeuta, Long> {
}
