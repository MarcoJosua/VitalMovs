package com.vitalmovs.repositories;

import com.vitalmovs.entities.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    List<Publicacion> findByForo_Id(Long foroId);
}
