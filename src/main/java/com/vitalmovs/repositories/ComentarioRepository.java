package com.vitalmovs.repositories;

import com.vitalmovs.entities.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByPublicacion_Id(Long publicacionId);
}
