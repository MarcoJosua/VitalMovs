package com.vitalmovs;

import com.vitalmovs.dtos.UserDTO;
import com.vitalmovs.entities.Authority;
import com.vitalmovs.entities.Fisioterapeuta;
import com.vitalmovs.entities.Paciente;
import com.vitalmovs.entities.User;
import com.vitalmovs.services.AuthorityService;
import com.vitalmovs.services.FisioterapeutaService;
import com.vitalmovs.services.PacienteService;
import com.vitalmovs.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VitalMovsApplication {

    public static void main(String[] args) {
        SpringApplication.run(VitalMovsApplication.class, args);
    }

    @Bean
    public CommandLineRunner startConfiguration(

            UserService userService,
            AuthorityService authorityService,
            PacienteService pacienteService,
            FisioterapeutaService fisioterapeutaService
    ){
        return args -> {


            Authority authority1 = authorityService.add(new Authority(null,"ROLE_ADMIN",null));
            Authority authority2 = authorityService.add(new Authority(null,"ROLE_PACIENTE",null));
            Authority authority3 = authorityService.add(new Authority(null,"ROLE_FISIOTERAPEUTA",null));


            userService.add(new UserDTO(null,"admin","pass","ROLE_ADMIN;ROLE_PACIENTE;ROLE_FISIOTERAPEUTA"));
            userService.add(new UserDTO(null,"paciente1@gmail.com","pass","ROLE_PACIENTE"));
            userService.add(new UserDTO(null,"paciente2@gmail.com","pass","ROLE_PACIENTE"));
            userService.add(new UserDTO(null,"paciente3@gmail.com","pass","ROLE_PACIENTE"));
            userService.add(new UserDTO(null,"fisioterapeuta1@gmail.com","pass","ROLE_FISIOTERAPEUTA"));
            userService.add(new UserDTO(null,"fisioterapeuta2@gmail.com","pass","ROLE_FISIOTERAPEUTA"));
            userService.add(new UserDTO(null,"fisioterapeuta3@gmail.com","pass","ROLE_FISIOTERAPEUTA"));



            User user1 = userService.findByUsername("paciente1@gmail.com");
            User user2 = userService.findByUsername("paciente2@gmail.com");
            User user3 = userService.findByUsername("paciente3@gmail.com");
            User user4 = userService.findByUsername("fisioterapeuta1@gmail.com");
            User user5 = userService.findByUsername("fisioterapeuta2@gmail.com");
            User user6 = userService.findByUsername("fisioterapeuta3@gmail.com");



            pacienteService.add(new Paciente(null,"Karin","Ugarte",2,"Femenino",user1,null));
            pacienteService.add(new Paciente(null,"Kelly","Guzman",2,"Femenino",user2,null));
            pacienteService.add(new Paciente(null,"Omar","Perez",2,"Masculino",user3,null));

            fisioterapeutaService.add(new Fisioterapeuta(null,"Cris","Vargas","Fisioterapia Clinica",user4,null));
            fisioterapeutaService.add(new Fisioterapeuta(null,"Taylor","Coma","Fisioterapia Deportiva",user5,null));
            fisioterapeutaService.add(new Fisioterapeuta(null,"Van","Cruz","Fisioterapia",user6,null));


        };
    }

}
