package com.vitalmovs.services;

import com.vitalmovs.dtos.EjercicioDTO;
import com.vitalmovs.entities.Ejercicio;
import java.util.List;

public interface EjercicioService {
    Ejercicio add(Ejercicio ejercicio);
    EjercicioDTO addDTO(EjercicioDTO ejercicioDTO);
    List<Ejercicio> listAll();
    List<EjercicioDTO> listAllDTO();
    Ejercicio findById(Long id);
    EjercicioDTO update(EjercicioDTO ejercicioDTO);
    void delete(Long id);
    List<EjercicioDTO> buscarPorNombreODescripcionDTO(String texto);
    List<EjercicioDTO> buscarPorNombreNativeDTO(String nombre);
}