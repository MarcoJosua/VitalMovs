package com.vitalmovs.serviceimpl;

import com.vitalmovs.dtos.UserDTO;
import com.vitalmovs.entities.Authority;
import com.vitalmovs.entities.Fisioterapeuta;
import com.vitalmovs.entities.Paciente;
import com.vitalmovs.entities.User;
import com.vitalmovs.exceptions.IncompleteDataException;
import com.vitalmovs.exceptions.KeyRepeatedDataExeception;
import com.vitalmovs.exceptions.ResourceNotFoundException;
import com.vitalmovs.repositories.FisioterapeutaRepository;
import com.vitalmovs.repositories.PacienteRepository;
import com.vitalmovs.repositories.UserRepository;
import com.vitalmovs.services.AuthorityService;
import com.vitalmovs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    AuthorityService authorityService;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    FisioterapeutaRepository fisioterapeutaRepository;

    @Override
    public User findById(Long id) {
        User userFound = userRepository.findById(id).orElse(null);
        if(userFound == null){
            throw new ResourceNotFoundException("User with Id: "+ id+" not found");
        }
        return userFound;
    }

    @Override
    public UserDTO findByIdDTO(Long id) {
        User user = findById(id);
        UserDTO userFound = new UserDTO(user.getId(), user.getUsername(), user.getPassword(),
                user.getAuthorities()
                        .stream()
                        .map( a-> a.getName())
                        .collect(Collectors.joining(";","",""))

        );
        return userFound;
    }

    @Override
    public User findByUsername(String username) {
        User userFound = userRepository.findByUsername(username);
        if(userFound == null){
            throw new ResourceNotFoundException("User with Username: "+ username+" not found");
        }
        return userFound;
    }


    private List<Authority> authoritiesFromString(String authorities) {

        List<Authority> authorityList = new ArrayList<>();
        List<String> authorityStringList = Arrays.stream(authorities.split(";")).toList();
        for (String authorityString : authorityStringList) {
            Authority authority = authorityService.findByName(authorityString);
            if (authority != null) {
                authorityList.add(authority);
            }
        }
        return authorityList;
    }

    @Override
    public User add(UserDTO userDTO) {

        if (userDTO.getUsername()==null || userDTO.getUsername().isBlank()) {
            throw new IncompleteDataException("Username can not be empty");
        }
        if (userDTO.getPassword()==null || userDTO.getPassword().isBlank()) {
            throw new IncompleteDataException("Password can not be empty");
        }
        if (userDTO.getAuthorities()==null||userDTO.getAuthorities().isBlank()) {
            throw new IncompleteDataException("Authorities can not be empty");
        }
        User newUser = new User(
                null, userDTO.getUsername(), userDTO.getPassword(), true, null
        );

        List<Authority> authorityList = authoritiesFromString(userDTO.getAuthorities());
        newUser.setAuthorities(authorityList);

        return add(newUser);
    }

    @Override
    public User add(User user) {

        User userFound = userRepository.findByUsername(user.getUsername());
        if(userFound != null){
            throw new KeyRepeatedDataExeception("Username: "+ user.getUsername()+" is already registeted");
        }

        //Paso 1: Validar si el username y el password cumplen con los requisitos: minimo, maximo, tipos carecteres

        //Paso 2: Encriptar el password
        user.setPassword( new BCryptPasswordEncoder().encode(user.getPassword())  );

        //Paso 3: Actualizar los campos adicionales segun tu logica de negocio
        user.setEnabled(true);

        User savedUser = userRepository.save(user);

        for (Authority authority : savedUser.getAuthorities()) {
            if ("ROLE_PACIENTE".equals(authority.getName())) {
                Paciente paciente = new Paciente();
                paciente.setNombre(savedUser.getUsername());
                paciente.setUser(savedUser);
                pacienteRepository.save(paciente);
            } else if ("ROLE_FISIOTERAPEUTA".equals(authority.getName())) {
                Fisioterapeuta fisio = new Fisioterapeuta();
                fisio.setNombre(savedUser.getUsername());
                fisio.setUser(savedUser);
                fisioterapeutaRepository.save(fisio);
            }
        }

        return savedUser;
    }
}