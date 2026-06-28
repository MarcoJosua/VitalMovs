package com.vitalmovs.serviceimpl;

import com.vitalmovs.dtos.AsignacionDTO;
import com.vitalmovs.entities.Fisioterapeuta;
import com.vitalmovs.entities.Foro;
import com.vitalmovs.entities.Paciente;
import com.vitalmovs.exceptions.ResourceNotFoundException;
import com.vitalmovs.services.FisioterapeutaService;
import com.vitalmovs.services.PacienteService;
import com.vitalmovs.services.PlanRehabilitacionService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vitalmovs.entities.Asignacion;
import com.vitalmovs.repositories.AsignacionRepository;
import com.vitalmovs.services.AsignacionService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignacionServiceImpl implements AsignacionService {

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private FisioterapeutaService fisioterapeutaService;

    @Override
    public Asignacion add(Asignacion asignacion) {
        if (asignacion.getMensaje().isBlank()) {
            throw new ValidationException("El mensaje de la asignacion no puede estar en blanco");
        }
        if (asignacion.getFecha() == null) {
            throw new ValidationException("La fecha de la asignacion no puede estar en blanco");
        }
        if (asignacion.getEstado().isBlank()) {
            throw new ValidationException("El estado de la asignacion no puede estar en blanco");
        }
        return asignacionRepository.save(asignacion);
    }

    @Override
    public AsignacionDTO addDTO(AsignacionDTO asignacionDTO) {
        // Recupera las entidades relacionadas por sus IDs (mismo patrón que TipoDiscapacidad en Foro)
        Paciente paciente = pacienteService.findById(asignacionDTO.getPacienteId());
        Fisioterapeuta fisioterapeuta = fisioterapeutaService.findById(asignacionDTO.getFisioterapeutaId());

        Asignacion nuevaAsignacion = new Asignacion(
                null,                            // id autogenerado
                asignacionDTO.getMensaje(),
                asignacionDTO.getFecha(),
                asignacionDTO.getEstado(),
                paciente,
                fisioterapeuta,
                null                          // planRehabilitacion (se asigna aparte si aplica)
        );

        nuevaAsignacion = add(nuevaAsignacion);
        asignacionDTO.setId(nuevaAsignacion.getId());
        return asignacionDTO;
    }


    @Override
    public Asignacion findById(Long id) {
        return asignacionRepository.findById(id).orElse(null);
    }

    @Override
    public AsignacionDTO update(Asignacion asignacion) {
        Asignacion foundAsignacion = asignacionRepository.findById(asignacion.getId()).orElse(null);
        if (foundAsignacion == null) {
            throw new ResourceNotFoundException("No se encontro la asignacion con id: " + asignacion.getId().toString());
        }
        if (asignacion.getMensaje() != null && !asignacion.getMensaje().isBlank()) {
            foundAsignacion.setMensaje(asignacion.getMensaje());
        }
        if (asignacion.getFecha() != null) {
            foundAsignacion.setFecha(asignacion.getFecha());
        }
        if (asignacion.getEstado() != null && !asignacion.getEstado().isBlank()) {
            foundAsignacion.setEstado(asignacion.getEstado());
        }
        asignacionRepository.save(foundAsignacion);
        return toDTO(foundAsignacion);
    }


    @Override
    public void delete(Long id) {
        Asignacion asignacion = asignacionRepository.findById(id).orElse(null);
        if (asignacion == null) {
            throw new ResourceNotFoundException("No se encontro la asignacion con id: " + id.toString());
        }
        asignacionRepository.delete(asignacion);
    }


    @Override
    public List<Asignacion> listAll() {
        return asignacionRepository.findAll();
    }

    @Override
    public List<AsignacionDTO> listAllDTO() {
        List<Asignacion> asignacionList = listAll();
        List<AsignacionDTO> asignacionDTOList = new ArrayList<>();
        for (Asignacion a : asignacionList) {
            asignacionDTOList.add(toDTO(a));
        }
        return asignacionDTOList;
    }

    private AsignacionDTO toDTO(Asignacion a) {
        return new AsignacionDTO(
                a.getId(),
                a.getMensaje(),
                a.getFecha(),
                a.getEstado(),
                a.getPaciente()           != null ? a.getPaciente().getId()            : null,
                a.getFisioterapeuta()     != null ? a.getFisioterapeuta().getId()       : null,
                a.getPlanRehabilitacion() != null ? a.getPlanRehabilitacion().getId()   : null,
                a.getPaciente() != null ? a.getPaciente().getNombre() : null,
                a.getFisioterapeuta() != null ? a.getFisioterapeuta().getNombre() : null
        );
    }

    @Override
    public List<AsignacionDTO> findByFisioterapeutaId(Long fisioterapeutaId) {

        List<Asignacion> asignacionList =  asignacionRepository.findByFisioterapeuta_User_Id(fisioterapeutaId);

        List<AsignacionDTO> asignacionDTOList = new ArrayList<>();

        for (Asignacion a : asignacionList) {
            asignacionDTOList.add(toDTO(a));
        }

        return asignacionDTOList;
    }


}
