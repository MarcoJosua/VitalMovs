package com.vitalmovs.repositories;

import com.vitalmovs.entities.Foro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForoRepository extends JpaRepository<Foro, Long> {
    List<Foro> findByTitulo(String titulo);
}
