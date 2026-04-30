package com.vitalmovs.services;

import com.vitalmovs.dtos.ComentarioDTO;
import com.vitalmovs.entities.Comentario;

import java.util.List;

public interface ComentarioService {
    public Comentario add(Comentario comentario);
    public ComentarioDTO addDTO(ComentarioDTO comentarioDTO);
    public Comentario findById(Long id);
    public List<Comentario> listByPublicacionId(Long publicacionId);
    public List<ComentarioDTO> listByPublicacionIdDTO(Long publicacionId);
    public Comentario update(Comentario comentario);
    public void delete(Long id);

}
