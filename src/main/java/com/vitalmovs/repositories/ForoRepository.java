package com.vitalmovs.repositories;

import com.vitalmovs.dtos.ForoVistaDTO;
import com.vitalmovs.entities.Foro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ForoRepository extends JpaRepository<Foro, Long> {
    List<Foro> findByTitulo(String titulo);

    // QUERY - JPQL - Obtener resumen de cada foro + cantidad de publicaciones, comentarios y última actividad.
    @Query("""
        SELECT new com.vitalmovs.dtos.ForoVistaDTO(
            f.id, f.titulo, f.descripcion,td.id,
            COUNT(DISTINCT p.id),
            COUNT(DISTINCT c.id),
            CASE
                WHEN MAX(p.fechaPublicacion) IS NULL AND MAX(c.fechaComentario) IS NULL THEN NULL
                WHEN MAX(p.fechaPublicacion) IS NULL THEN MAX(c.fechaComentario)
                WHEN MAX(c.fechaComentario) IS NULL THEN MAX(p.fechaPublicacion)
                WHEN MAX(p.fechaPublicacion) >= MAX(c.fechaComentario) THEN MAX(p.fechaPublicacion)
                ELSE MAX(c.fechaComentario)
            END
        )
        FROM Foro f
        LEFT JOIN f.tipoDiscapacidad td
        LEFT JOIN f.publicaciones p
        LEFT JOIN p.comentarios c
        WHERE td IS NULL
           OR td.id IN (
                SELECT pd.tipoDiscapacidad.id 
                FROM PacienteDiscapacidad pd
                WHERE pd.paciente.id = :pacienteId
           )
        GROUP BY f.id, f.titulo, f.descripcion, td.id
        ORDER BY f.titulo
    """)
    List<ForoVistaDTO> listarForosVisiblesConResumenPorPaciente(@Param("pacienteId") Long pacienteId);
}
