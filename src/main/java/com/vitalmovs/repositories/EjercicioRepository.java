package com.vitalmovs.repositories;

import com.vitalmovs.entities.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {
    List<Ejercicio> findByNombre(String nombre);
}