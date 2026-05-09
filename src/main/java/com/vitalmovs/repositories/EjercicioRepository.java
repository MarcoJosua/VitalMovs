package com.vitalmovs.repositories;

import com.vitalmovs.entities.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {
    // 1. QUERY METHOD
    // Busca ejercicio por nombre exacto
    List<Ejercicio> findByNombre(String nombre);
    // 2. JPQL QUERY
    // Busca ejercicios cuyo nombre o descripcion contenga un texto
    @Query("SELECT e FROM Ejercicio e WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) OR LOWER(e.descripcion) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Ejercicio> buscarPorNombreODescripcion(@Param("texto") String texto);
    // 3. NATIVE QUERY
    // Busca ejercicios por nombre usando SQL nativo
    @Query(value = "SELECT * FROM ejercicios WHERE LOWER(nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))", nativeQuery = true)
    List<Ejercicio> buscarPorNombreNative(@Param("nombre") String nombre);
}