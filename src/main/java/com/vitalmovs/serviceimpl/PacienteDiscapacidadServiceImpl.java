package com.vitalmovs.serviceimpl;

import com.vitalmovs.dtos.PacienteDiscapacidadDTO;
import com.vitalmovs.entities.Paciente;
import com.vitalmovs.entities.PacienteDiscapacidad;
import com.vitalmovs.entities.TipoDiscapacidad;
import com.vitalmovs.exceptions.ResourceNotFoundException;
import com.vitalmovs.repositories.PacienteDiscapacidadRepository;
import com.vitalmovs.services.PacienteDiscapacidadService;
import com.vitalmovs.services.PacienteService;
import com.vitalmovs.services.TipoDiscapacidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteDiscapacidadServiceImpl implements PacienteDiscapacidadService {

    @Autowired
    private PacienteDiscapacidadRepository pacienteDiscapacidadRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private TipoDiscapacidadService tipoDiscapacidadService;

    @Override
    public PacienteDiscapacidad add(PacienteDiscapacidad pacienteDiscapacidad) {
        pacienteDiscapacidad = pacienteDiscapacidadRepository.save(pacienteDiscapacidad);
        return pacienteDiscapacidad;
    }

    @Override
    public PacienteDiscapacidadDTO addDTO(PacienteDiscapacidadDTO pacienteDiscapacidadDTO) {
        Paciente paciente = pacienteService.findById(pacienteDiscapacidadDTO.getPacienteId());
        TipoDiscapacidad tipoDiscapacidad = tipoDiscapacidadService.findById(pacienteDiscapacidadDTO.getTipoDiscapacidadId());

        PacienteDiscapacidad newPD = new PacienteDiscapacidad(
                null,
                tipoDiscapacidad,
                paciente
        );
        newPD = add(newPD);
        pacienteDiscapacidadDTO.setId(newPD.getId());
        return pacienteDiscapacidadDTO;
    }

    @Override
    public PacienteDiscapacidad findById(Long id) {
        return pacienteDiscapacidadRepository.findById(id).orElse(null);
    }

    @Override
    public List<PacienteDiscapacidad> listByPacienteId(Long pacienteId) {
        return pacienteDiscapacidadRepository.findByPaciente_Id(pacienteId);
    }

    @Override
    public List<PacienteDiscapacidadDTO> listByPacienteIdDTO(Long pacienteId) {
        List<PacienteDiscapacidad> pdList = listByPacienteId(pacienteId);
        List<PacienteDiscapacidadDTO> pdDTOList = new ArrayList<>();
        for (PacienteDiscapacidad pd : pdList) {
            pdDTOList.add(new PacienteDiscapacidadDTO(
                    pd.getId(),
                    pd.getTipoDiscapacidad().getId(),
                    pd.getPaciente().getId()
            ));
        }
        return pdDTOList;
    }

    @Override
    public PacienteDiscapacidad update(PacienteDiscapacidad pacienteDiscapacidad) {
        PacienteDiscapacidad foundPD = findById(pacienteDiscapacidad.getId());
        if (foundPD == null) {
            throw new ResourceNotFoundException("No se encontro el registro con id: " + pacienteDiscapacidad.getId().toString());
        }
        if (pacienteDiscapacidad.getPaciente() != null) {
            foundPD.setPaciente(pacienteDiscapacidad.getPaciente());
        }
        if (pacienteDiscapacidad.getTipoDiscapacidad() != null) {
            foundPD.setTipoDiscapacidad(pacienteDiscapacidad.getTipoDiscapacidad());
        }
        pacienteDiscapacidad = pacienteDiscapacidadRepository.save(foundPD);
        return pacienteDiscapacidad;
    }

    @Override
    public void delete(Long id) {
        if (findById(id) == null) {
            throw new ResourceNotFoundException("No se encontro el registro con id: " + id.toString());
        }
        pacienteDiscapacidadRepository.deleteById(id);
    }
}

