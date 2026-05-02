package com.vitalmovs.services;
import com.vitalmovs.dtos.HistorialDolorDTO;
import com.vitalmovs.entities.Estadistica;

import java.util.List;


public interface EstadisticaService {

    public void insert(Estadistica estadistica); // C
    public void update(Estadistica estadistica); // U: Update
    public List<Estadistica> list();             // R
    public void delete(Long idEstadistica);      // D
    public Estadistica listId(Long idEstadistica); // Para buscar antes de editar
    public List<HistorialDolorDTO> obtenerHistorialPorPlan(Long idPlan);


}
