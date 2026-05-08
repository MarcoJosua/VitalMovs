package com.vitalmovs.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vitalmovs.entities.Asignacion;
import com.vitalmovs.repositories.AsignacionRepository;
import com.vitalmovs.services.AsignacionService;

import java.util.List;

@Service
public class AsignacionServiceImpl implements AsignacionService {

    @Autowired
    private AsignacionRepository aR; // Inyectamos el repositorio

    @Override
    public void add(Asignacion asignacion) {
        aR.save(asignacion); // Sirve para insertar y actualizar
    }

    @Override
    public void update(Asignacion asignacion) {
        aR.save(asignacion);
    }

    @Override
    public List<Asignacion> list() {
        return aR.findAll(); // Retorna toda la lista
    }

    @Override
    public void delete(Long idAsignacion) {
        aR.deleteById(idAsignacion); // Elimina por ID
    }

    @Override
    public Asignacion listId(Long idAsignacion) {
        return aR.findById(idAsignacion).orElse(new Asignacion());
    }
}
