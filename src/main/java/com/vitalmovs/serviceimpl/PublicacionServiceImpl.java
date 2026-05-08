package com.vitalmovs.serviceimpl;

import com.vitalmovs.dtos.PublicacionDTO;
import com.vitalmovs.entities.Foro;
import com.vitalmovs.entities.Paciente;
import com.vitalmovs.entities.Publicacion;
import com.vitalmovs.exceptions.ResourceNotFoundException;
import com.vitalmovs.repositories.PublicacionRepository;
import com.vitalmovs.services.ForoService;
import com.vitalmovs.services.PacienteService;
import com.vitalmovs.services.PublicacionService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublicacionServiceImpl implements PublicacionService {
    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private ForoService foroService;

    @Autowired
    private PacienteService pacienteService;

    @Override
    public Publicacion add(Publicacion publicacion) {

        if (publicacion.getTitulo().isBlank()) {
            throw new ValidationException("El titulo de la publicacion no puede estar en blanco");
        }
        if (publicacion.getContenido().isBlank()) {
            throw new ValidationException("El contenido de la publicacion no puede estar en blanco");
        }
        if (publicacion.getFechaPublicacion() == null) {
            throw new ValidationException("La fecha de publicacion no puede estar en blanco");
        }

        publicacion = publicacionRepository.save(publicacion);
        return publicacion;
    }

    @Override
    public PublicacionDTO addDTO(PublicacionDTO publicacionDTO) {
        Foro foro = foroService.findById(publicacionDTO.getForoId());
        Paciente paciente = pacienteService.findById(publicacionDTO.getPacienteId());

        Publicacion newPublicacion = new Publicacion(
                null,
                publicacionDTO.getTitulo(),
                publicacionDTO.getContenido(),
                publicacionDTO.getFechaPublicacion(),
                foro,
                paciente,
                null
        );

        newPublicacion = add(newPublicacion);
        publicacionDTO.setId(newPublicacion.getId());
        return publicacionDTO;
    }

    @Override
    public Publicacion findById(Long id) {
        return publicacionRepository.findById(id).orElse(null);
    }



    @Override
    public Publicacion update(Publicacion publicacion) {
        Publicacion oldPublicacion = findById(publicacion.getId());
        if(oldPublicacion == null){
            throw new ResourceNotFoundException("No se encontro la publicacion con id: "+ publicacion.getId().toString());
        }
        if (publicacion.getTitulo() != null && !publicacion.getTitulo().isBlank()) {
            oldPublicacion.setTitulo(publicacion.getTitulo());
        }
        if (publicacion.getContenido() != null && !publicacion.getContenido().isBlank()) {
            oldPublicacion.setContenido(publicacion.getContenido());
        }
        if (publicacion.getFechaPublicacion() != null) {
            oldPublicacion.setFechaPublicacion(publicacion.getFechaPublicacion());
        }

        publicacion = publicacionRepository.save(oldPublicacion);
        return publicacion;
    }

    @Override
    public void delete(Long id) {
        Publicacion publicacion = findById(id);
        if (publicacion == null) {
            throw new ResourceNotFoundException("No se encontro la publicacion con id: " + id);
        }
        publicacionRepository.delete(publicacion);
    }

    @Override
    public List<Publicacion> listarPublicacionesPorForoFecha(Long foroId) {
        return publicacionRepository.findByForo_IdOrderByFechaPublicacionDesc(foroId);
    }

    @Override
    public List<PublicacionDTO> listarPublicacionesPorForoFechaDTO(Long foroId) {
        List<Publicacion> publicacionList = listarPublicacionesPorForoFecha(foroId);
        List<PublicacionDTO> publicacionDTOList = new ArrayList<>();
        for (Publicacion p : publicacionList) {
            publicacionDTOList.add(new PublicacionDTO(
                    p.getId(),
                    p.getTitulo(),
                    p.getContenido(),
                    p.getFechaPublicacion(),
                    p.getForo().getId(),
                    p.getPaciente().getId()
            ));
        }
        return publicacionDTOList;
    }

    @Override
    public List<PublicacionDTO> listarPublicacionesPorRelevanciaDTO(Long foroId) {
        List<Publicacion> publicacionList = publicacionRepository.listarPublicacionesPorRelevancia(foroId);
        List<PublicacionDTO> publicacionDTOList = new ArrayList<>();
        for (Publicacion p : publicacionList) {
            publicacionDTOList.add(new PublicacionDTO(
                    p.getId(), p.getTitulo(), p.getContenido(), p.getFechaPublicacion(), p.getForo().getId(), p.getPaciente().getId()
            ));
        }
        return publicacionDTOList;
    }
}
