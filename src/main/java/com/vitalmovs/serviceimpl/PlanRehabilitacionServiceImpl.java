package com.vitalmovs.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vitalmovs.entities.PlanRehabilitacion;
import com.vitalmovs.repositories.PlanRehabilitacionRepository;
import com.vitalmovs.services.PlanRehabilitacionService;

import java.util.List;

@Service
public class PlanRehabilitacionServiceImpl implements PlanRehabilitacionService {

    @Autowired
    private PlanRehabilitacionRepository pR; // Iniciales de PlanRehabilitacionRepository

    @Override
    public void insert(PlanRehabilitacion planRehabilitacion) {
        pR.save(planRehabilitacion);
    }

    @Override
    public void update(PlanRehabilitacion planRehabilitacion) {
        pR.save(planRehabilitacion);
    }

    @Override
    public List<PlanRehabilitacion> list() {
        return pR.findAll();
    }

    @Override
    public void delete(Long idPlan) {
        pR.deleteById(idPlan);
    }

    @Override
    public PlanRehabilitacion listId(Long idPlan) {
        return pR.findById(idPlan).orElse(new PlanRehabilitacion());
    }

}
