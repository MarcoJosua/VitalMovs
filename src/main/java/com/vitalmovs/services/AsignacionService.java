package com.vitalmovs.services;

import com.vitalmovs.entities.Asignacion;

import java.util.List;

public interface AsignacionService {

    public void insert(Asignacion asignacion); // C: Create
    public void update(Asignacion asignacion); // U: Update
    public List<Asignacion> list();            // R: Read
    public void delete(Long idAsignacion);    // D: Delete
    public Asignacion listId(Long idAsignacion); // Para el Update (buscar uno)

}
