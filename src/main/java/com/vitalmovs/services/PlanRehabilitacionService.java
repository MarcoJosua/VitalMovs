package com.vitalmovs.services;
import com.vitalmovs.entities.PlanRehabilitacion;

import java.util.List;


public interface PlanRehabilitacionService {

    public void insert(PlanRehabilitacion planRehabilitacion); // C
    public void update(PlanRehabilitacion planRehabilitacion); // U: Update
    public List<PlanRehabilitacion> list();                    // R
    public void delete(Long idPlan);                           // D
    public PlanRehabilitacion listId(Long idPlan);             // Para buscar uno
}
