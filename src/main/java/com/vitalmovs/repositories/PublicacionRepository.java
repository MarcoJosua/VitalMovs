package com.vitalmovs.repositories;

import com.vitalmovs.dtos.PublicacionDTO;
import com.vitalmovs.entities.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    List<Publicacion> findByForo_Id(Long foroId);

    // QUERY - QUERY METHOD - Listar publicaciones de un foro ordenadas por fecha descendente.
    List<Publicacion> findByForo_IdOrderByFechaPublicacionDesc(Long foroId);


    // QUERY - Native Query - Listar publicaciones por relevancia (mayor cantidad de comentarios).
    @Query(value = """
            SELECT p.*
            FROM publicaciones p
            LEFT JOIN comentarios c
                ON c.publicacion_id = p.id
            WHERE p.foro_id = :foroId
            GROUP BY 
                p.id,
                p.titulo,
                p.contenido,
                p.fecha_publicacion,
                p.foro_id,
                p.paciente_id
            ORDER BY COUNT(c.id) DESC, p.fecha_publicacion DESC
        """, nativeQuery = true)
    List<Publicacion> listarPublicacionesPorRelevancia(@Param("foroId") Long foroId);
}
