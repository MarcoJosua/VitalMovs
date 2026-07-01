package com.vitalmovs.repositories;

import com.vitalmovs.dtos.AsignacionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vitalmovs.entities.Asignacion;

import java.util.List;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
    List<Asignacion> findByFisioterapeuta_User_Id(Long userId);
    List<Asignacion> findByPaciente_User_Id(Long userId);

}
