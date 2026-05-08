package com.vitalmovs.serviceimpl;

import com.vitalmovs.dtos.ForoDTO;
import com.vitalmovs.dtos.ForoVistaDTO;
import com.vitalmovs.entities.Foro;
import com.vitalmovs.entities.TipoDiscapacidad;
import com.vitalmovs.exceptions.ResourceNotFoundException;
import com.vitalmovs.repositories.ForoRepository;
import com.vitalmovs.services.ForoService;
import com.vitalmovs.services.TipoDiscapacidadService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForoServiceImpl implements ForoService {
    @Autowired
    private ForoRepository foroRepository;

    @Autowired
    private TipoDiscapacidadService tipoDiscapacidadService;

    @Override
    public Foro add(Foro foro) {
        if (foro.getTitulo().isBlank()) {
            throw new ValidationException("El titulo del foro no puede estar en blanco");
        }
        if (foro.getDescripcion().isBlank()) {
            throw new ValidationException("La descripcion del foro no puede estar en blanco");
        }

        if (!foroRepository.findByTitulo(foro.getTitulo()).isEmpty()) {
            //return null;
            throw new ValidationException("El titulo: "+ foro.getTitulo() + " ya esta registrado");
        }

        foro = foroRepository.save(foro);
        return foro;
    }

    @Override
    public ForoDTO addDTO(ForoDTO foroDTO) {
        TipoDiscapacidad tipoDiscapacidad = tipoDiscapacidadService.findById(foroDTO.getTipoDiscapacidadId());
        Foro newForo = new Foro(
                null,
                foroDTO.getTitulo(),
                foroDTO.getDescripcion(),
                tipoDiscapacidad,
                null
        );
        newForo = add(newForo);
        foroDTO.setId(newForo.getId());
        return foroDTO;
    }

    @Override
    public List<Foro> listAll() {
        return foroRepository.findAll();
    }

    @Override
    public List<ForoDTO> listAllDTO() {
        List<Foro> foroList = listAll();
        List<ForoDTO> foroDTOList = new ArrayList<>();
        for(Foro f: foroList) {
            foroDTOList.add(new ForoDTO(
                    f.getId(),
                    f.getTitulo(),
                    f.getDescripcion(),
                    f.getTipoDiscapacidad() != null ? f.getTipoDiscapacidad().getId() : null // Esto se hace porque habra un foro general
            ));
        }
        return foroDTOList;
    }

    @Override
    public Foro findById(Long id) {
        return foroRepository.findById(id).orElse(null);
    }

    @Override
    public Foro update(Foro foro) {
        Foro foundForo = findById(foro.getId());
        if (foundForo==null) {
            throw new ResourceNotFoundException("No se encontro el foro con id: "+ foro.getId().toString());
        }
        if (foro.getTitulo()!=null && !foro.getTitulo().isBlank()) {
            foundForo.setTitulo(foro.getTitulo());
        }
        if (foro.getDescripcion()!=null && !foro.getDescripcion().isBlank()) {
            foundForo.setDescripcion(foro.getDescripcion());
        }

        foro = foroRepository.save(foundForo);
        return foro;
    }

    @Override
    public void delete(Long id) {
        Foro foro = findById(id);
        if(foro == null){
            throw new ResourceNotFoundException("No se encontro el foro con id: "+id.toString());
        }
        foroRepository.delete(foro);
    }

    @Override
    public List<ForoVistaDTO> listarForosVisiblesConResumenPorPaciente(Long pacienteId) {
        return foroRepository.listarForosVisiblesConResumenPorPaciente(pacienteId);
    }


}
