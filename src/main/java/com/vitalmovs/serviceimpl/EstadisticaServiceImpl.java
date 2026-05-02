package com.vitalmovs.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vitalmovs.dtos.HistorialDolorDTO;
import com.vitalmovs.entities.Estadistica;
import com.vitalmovs.repositories.EstadisticaRepository;
import com.vitalmovs.services.EstadisticaService;

import java.util.List;

@Service
public class EstadisticaServiceImpl implements EstadisticaService {

    @Autowired
    private EstadisticaRepository eR; // Siguiendo tu estilo de iniciales (EstadisticaRepository)

    @Override
    public void insert(Estadistica estadistica) {
        eR.save(estadistica);
    }

    @Override
    public void update(Estadistica estadistica) {
        eR.save(estadistica);
    }

    @Override
    public List<Estadistica> list() {
        return eR.findAll();
    }

    @Override
    public void delete(Long idEstadistica) {
        eR.deleteById(idEstadistica);
    }

    @Override
    public Estadistica listId(Long idEstadistica) {
        return eR.findById(idEstadistica).orElse(new Estadistica());
    }

    @Override
    public List<HistorialDolorDTO> obtenerHistorialPorPlan(Long idPlan) {
        return eR.obtenerHistorialPorPlan(idPlan);
    }

}
