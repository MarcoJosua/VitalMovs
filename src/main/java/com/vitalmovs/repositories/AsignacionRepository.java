package com.vitalmovs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vitalmovs.entities.Asignacion;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
}
