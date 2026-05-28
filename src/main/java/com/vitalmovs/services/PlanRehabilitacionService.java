package com.vitalmovs.services;
import com.vitalmovs.dtos.EstadisticaDTO;
import com.vitalmovs.dtos.PlanRehabilitacionDTO;
import com.vitalmovs.entities.Estadistica;
import com.vitalmovs.entities.PlanRehabilitacion;

import java.util.List;


public interface PlanRehabilitacionService {

    public PlanRehabilitacion add(PlanRehabilitacion planRehabilitacion);

    public PlanRehabilitacionDTO addDTO(PlanRehabilitacionDTO planRehabilitacionDTO);

    public PlanRehabilitacion findById(Long id);

    public List<PlanRehabilitacionDTO> listAllDTO();

    public PlanRehabilitacionDTO update(PlanRehabilitacionDTO planRehabilitacionDTO);

    public PlanRehabilitacion delete(Long id);
}
