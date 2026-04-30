package com.vitalmovs.serviceimpl;

import com.vitalmovs.dtos.ComentarioDTO;
import com.vitalmovs.entities.Comentario;
import com.vitalmovs.entities.Publicacion;
import com.vitalmovs.exceptions.ResourceNotFoundException;
import com.vitalmovs.repositories.ComentarioRepository;
import com.vitalmovs.services.ComentarioService;
import com.vitalmovs.services.PublicacionService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComentarioServiceImpl implements ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PublicacionService publicacionService;

    //@Autowired
    //private PacienteService pacienteService;

    @Override
    public Comentario add(Comentario comentario) {
        if (comentario.getContenido().isBlank()) {
            throw new ValidationException("El comentario en la publicacion no puede estar en blanco");
        }
        if (comentario.getFechaComentario() == null) {
            throw new ValidationException("La fecha de comentario no puede estar en blanco");
        }
        comentario = comentarioRepository.save(comentario);
        return comentario;
    }

    @Override
    public ComentarioDTO addDTO(ComentarioDTO comentarioDTO) {
        Publicacion publicacion = publicacionService.findById(comentarioDTO.getPublicacionId());
        // Paciente paciente = pacienteService.findById(comentarioDTO.getPacienteId());

        Comentario newComentario = new Comentario(
                null,
                comentarioDTO.getContenido(),
                comentarioDTO.getFechaComentario(),
                publicacion
                //paciente,
        );

        newComentario = add(newComentario);
        comentarioDTO.setId(newComentario.getId());
        return comentarioDTO;
    }

    @Override
    public Comentario findById(Long id) {
        return comentarioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Comentario> listByPublicacionId(Long publicacionId) {
        return comentarioRepository.findByPublicacion_Id(publicacionId);
    }

    @Override
    public List<ComentarioDTO> listByPublicacionIdDTO(Long publicacionId) {
        List<Comentario> comentarioList = listByPublicacionId(publicacionId);
        List<ComentarioDTO> comentarioDTOList = new ArrayList<>();
        for (Comentario c : comentarioList) {
            comentarioDTOList.add(new ComentarioDTO(
                    c.getId(),
                    c.getContenido(),
                    c.getFechaComentario(),
                    c.getPublicacion().getId()
                    //p.getPaciente().getId()
            ));
        }
        return comentarioDTOList;
    }

    @Override
    public Comentario update(Comentario comentario) {
        Comentario foundComentario = findById(comentario.getId());
        if(foundComentario == null){
            throw new ResourceNotFoundException("No se encontro el comentario con id: "+ comentario.getId().toString());
        }
        if (comentario.getContenido() != null && !comentario.getContenido().isBlank()) {
            foundComentario.setContenido(comentario.getContenido());
        }
        if (comentario.getFechaComentario() != null) {
            foundComentario.setFechaComentario(comentario.getFechaComentario());
        }
        comentario = comentarioRepository.save(foundComentario);
        return comentario;
    }

    @Override
    public void delete(Long id) {
        if(findById(id) == null){
            throw new ResourceNotFoundException("No se encontro el comentario con id: "+id.toString());
        }
        comentarioRepository.deleteById(id);

    }
}
