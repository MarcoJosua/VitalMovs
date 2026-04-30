package com.vitalmovs.services;

import com.vitalmovs.dtos.PublicacionDTO;
import com.vitalmovs.entities.Publicacion;

import java.util.List;

public interface PublicacionService {
    public Publicacion add(Publicacion publicacion);
    public PublicacionDTO addDTO(PublicacionDTO publicacionDTO);
    public Publicacion findById(Long id);
    public List<Publicacion> listByForoId(Long foroId);
    public List<PublicacionDTO> listByForoIdDTO(Long foroId);
    public Publicacion update(Publicacion publicacion);
    public void delete(Long id);
}
