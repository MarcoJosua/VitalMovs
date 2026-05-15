package com.vitalmovs.services;
import com.vitalmovs.dtos.AsignacionDTO;
import com.vitalmovs.dtos.EstadisticaDTO;
import com.vitalmovs.dtos.HistorialDolorDTO;
import com.vitalmovs.entities.Estadistica;

import java.util.List;


public interface EstadisticaService {

    public Estadistica add(Estadistica estadistica);

    public EstadisticaDTO addDTO(EstadisticaDTO estadisticaDTO);

    public EstadisticaDTO findById(Long id);

    public List<Estadistica> findAll();
    public List<EstadisticaDTO> listAllDTO();


    public EstadisticaDTO update(EstadisticaDTO estadisticaDTO);

    public Estadistica delete(Long id);

    List<Estadistica> findByPlanNative(Long idPlan);

}
