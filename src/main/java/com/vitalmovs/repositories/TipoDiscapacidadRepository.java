package com.vitalmovs.repositories;
 
import com.vitalmovs.entities.TipoDiscapacidad;
import org.springframework.data.jpa.repository.JpaRepository;
 
import java.util.List;
 
public interface TipoDiscapacidadRepository extends JpaRepository<TipoDiscapacidad, Long> {
    List<TipoDiscapacidad> findByNombre(String nombre);
}
