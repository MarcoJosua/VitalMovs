package com.vitalmovs.serviceimpl;

import com.vitalmovs.dtos.FisioterapeutaDTO;
import com.vitalmovs.entities.Fisioterapeuta;
import com.vitalmovs.entities.User;
import com.vitalmovs.exceptions.ResourceNotFoundException;
import com.vitalmovs.services.UserService;
import jakarta.validation.ValidationException;
import com.vitalmovs.repositories.FisioterapeutaRepository;
import com.vitalmovs.services.FisioterapeutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FisioterapeutaServiceImpl implements FisioterapeutaService {

    @Autowired
    private FisioterapeutaRepository fisioterapeutaRepository;

    @Autowired
    private UserService userService;

    @Override
    public Fisioterapeuta add(Fisioterapeuta fisioterapeuta) {
        // Validar nombre
        if (fisioterapeuta.getNombre() == null || fisioterapeuta.getNombre().isBlank())
            throw new ValidationException("El nombre del fisioterapeuta no puede estar en blanco");
        // Validar apellido
        if (fisioterapeuta.getApellido() == null || fisioterapeuta.getApellido().isBlank())
            throw new ValidationException("El apellido del fisioterapeuta no puede estar en blanco");
        // Validar especialidad
        if (fisioterapeuta.getEspecialidad() == null || fisioterapeuta.getEspecialidad().isBlank())
            throw new ValidationException("La especialidad del fisioterapeuta no puede estar en blanco");
        // Validar que tenga un user vinculado
        if (fisioterapeuta.getUser() == null || fisioterapeuta.getUser().getId() == null)
            throw new ValidationException("El fisioterapeuta debe tener un userId válido");

        return fisioterapeutaRepository.save(fisioterapeuta);
    }

    @Override
    public FisioterapeutaDTO addDTO(FisioterapeutaDTO dto) {
        // Validar userId en el DTO antes de buscar
        if (dto.getUserId() == null)
            throw new ValidationException("El userId no puede ser nulo");

        User user = userService.findById(dto.getUserId());
        if (user == null)
            throw new ResourceNotFoundException("No se encontró el usuario con id: " + dto.getUserId());

        Fisioterapeuta f = new Fisioterapeuta(null, dto.getNombre(), dto.getApellido(), dto.getEspecialidad(), user, null);
        f = add(f);
        dto.setId(f.getId());
        return dto;
    }

    @Override
    public FisioterapeutaDTO findByUserId(Long userId) {

        Fisioterapeuta f = fisioterapeutaRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró fisioterapeuta para el usuario con id: " + userId
                ));

        return new FisioterapeutaDTO(
                f.getId(),
                f.getNombre(),
                f.getApellido(),
                f.getEspecialidad(),
                f.getUser().getId()
        );
    }

    @Override
    public List<Fisioterapeuta> listAll() {
        return fisioterapeutaRepository.findAll();
    }

    @Override
    public List<FisioterapeutaDTO> listAllDTO() {
        List<FisioterapeutaDTO> dtoList = new ArrayList<>();
        for (Fisioterapeuta f : listAll()) {
            // Validar que el user no sea null antes de llamar getId()
            Long userId = (f.getUser() != null) ? f.getUser().getId() : null;
            dtoList.add(new FisioterapeutaDTO(f.getId(), f.getNombre(), f.getApellido(), f.getEspecialidad(), userId));
        }
        return dtoList;
    }

    @Override
    public Fisioterapeuta findById(Long id) {
        // Validar que el id no sea null
        if (id == null)
            throw new ValidationException("El id del fisioterapeuta no puede ser nulo");
        return fisioterapeutaRepository.findById(id).orElse(null);
    }

    @Override
    public Fisioterapeuta update(Fisioterapeuta fisioterapeuta) {
        // Validar que venga el id para saber qué registro actualizar
        if (fisioterapeuta.getId() == null)
            throw new ValidationException("El id del fisioterapeuta es obligatorio para actualizar");

        Fisioterapeuta found = findById(fisioterapeuta.getId());
        if (found == null)
            throw new ResourceNotFoundException("No se encontró el fisioterapeuta con id: " + fisioterapeuta.getId());

        // Actualizar nombre si viene
        if (fisioterapeuta.getNombre() != null && !fisioterapeuta.getNombre().isBlank())
            found.setNombre(fisioterapeuta.getNombre());

        // Actualizar apellido si viene
        if (fisioterapeuta.getApellido() != null && !fisioterapeuta.getApellido().isBlank())
            found.setApellido(fisioterapeuta.getApellido());

        // Actualizar especialidad si viene
        if (fisioterapeuta.getEspecialidad() != null && !fisioterapeuta.getEspecialidad().isBlank())
            found.setEspecialidad(fisioterapeuta.getEspecialidad());

        // Actualizar user si viene un userId válido
        if (fisioterapeuta.getUser() != null && fisioterapeuta.getUser().getId() != null) {
            User user = userService.findById(fisioterapeuta.getUser().getId());
            if (user == null)
                throw new ResourceNotFoundException("No se encontró el usuario con id: " + fisioterapeuta.getUser().getId());
            found.setUser(user);
        }

        return fisioterapeutaRepository.save(found);
    }

    @Override
    public void delete(Long id) {
        if (id == null)
            throw new ValidationException("El id del fisioterapeuta no puede ser nulo para eliminar");
        if (findById(id) == null)
            throw new ResourceNotFoundException("No se encontró el fisioterapeuta con id: " + id);
        fisioterapeutaRepository.deleteById(id);
    }

    @Override
    public List<Fisioterapeuta> buscarPorNombreOApellido(String texto) {
        return fisioterapeutaRepository.buscarPorNombreOApellido(texto);
    }
    @Override
    public List<Fisioterapeuta> findByEspecialidad(String especialidad) {
        return fisioterapeutaRepository.findByEspecialidadIgnoreCase(especialidad);
    }
    @Override
    public List<Fisioterapeuta> findByEspecialidadNative(String especialidad) {
        return fisioterapeutaRepository.findByEspecialidadNative(especialidad);
    }
}

