package com.vitalmovs;

import com.vitalmovs.dtos.UserDTO;
import com.vitalmovs.entities.*;
import com.vitalmovs.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;


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
            FisioterapeutaService fisioterapeutaService,

            TipoDiscapacidadService tipoDiscapacidadService,
            PacienteDiscapacidadService pacienteDiscapacidadService,
            FisioterapeutaDiscapacidadService fisioterapeutaDiscapacidadService,

            ForoService foroService,
            PublicacionService publicacionService,
            ComentarioService comentarioService,

            AsignacionService asignacionService,
            PlanRehabilitacionService planRehabilitacionService,
            EjercicioService ejercicioService,
            PlanEjercicioService planEjercicioService,
            EstadisticaService estadisticaService

    ){
        return args -> {

            // =========================
            // 1. Crear autoridades
            // =========================

            authorityService.add(new Authority(null, "ROLE_ADMIN", null));
            authorityService.add(new Authority(null, "ROLE_PACIENTE", null));
            authorityService.add(new Authority(null, "ROLE_FISIOTERAPEUTA", null));


            // =========================
            // 2. Crear usuarios
            // =========================

            userService.add(new UserDTO(null, "admin", "pass", "ROLE_ADMIN"));

            userService.add(new UserDTO(null, "paciente1@gmail.com", "pass", "ROLE_PACIENTE"));
            userService.add(new UserDTO(null, "paciente2@gmail.com", "pass", "ROLE_PACIENTE"));
            userService.add(new UserDTO(null, "paciente3@gmail.com", "pass", "ROLE_PACIENTE"));

            userService.add(new UserDTO(null, "fisioterapeuta1@gmail.com", "pass", "ROLE_FISIOTERAPEUTA"));
            userService.add(new UserDTO(null, "fisioterapeuta2@gmail.com", "pass", "ROLE_FISIOTERAPEUTA"));
            userService.add(new UserDTO(null, "fisioterapeuta3@gmail.com", "pass", "ROLE_FISIOTERAPEUTA"));


            // =========================
            // 3. Recuperar usuarios creados
            // =========================

            User userPaciente1 = userService.findByUsername("paciente1@gmail.com");
            User userPaciente2 = userService.findByUsername("paciente2@gmail.com");
            User userPaciente3 = userService.findByUsername("paciente3@gmail.com");

            User userFisio1 = userService.findByUsername("fisioterapeuta1@gmail.com");
            User userFisio2 = userService.findByUsername("fisioterapeuta2@gmail.com");
            User userFisio3 = userService.findByUsername("fisioterapeuta3@gmail.com");


            // =========================
            // 4. Crear pacientes
            // =========================

            Paciente paciente1 = pacienteService.add(
                    new Paciente(null, "Karin", "Ugarte", 22, "Femenino", userPaciente1, null)
            );

            Paciente paciente2 = pacienteService.add(
                    new Paciente(null, "Kelly", "Guzman", 24, "Femenino", userPaciente2, null)
            );

            Paciente paciente3 = pacienteService.add(
                    new Paciente(null, "Omar", "Perez", 25, "Masculino", userPaciente3, null)
            );


            // =========================
            // 5. Crear fisioterapeutas
            // =========================

            Fisioterapeuta fisio1 = fisioterapeutaService.add(
                    new Fisioterapeuta(null, "Cris", "Vargas", "Fisioterapia clínica", userFisio1, null)
            );

            Fisioterapeuta fisio2 = fisioterapeutaService.add(
                    new Fisioterapeuta(null, "Taylor", "Coma", "Fisioterapia deportiva", userFisio2, null)
            );

            Fisioterapeuta fisio3 = fisioterapeutaService.add(
                    new Fisioterapeuta(null, "Van", "Cruz", "Fisioterapia general", userFisio3, null)
            );


            // =========================
            // 6. Crear tipos de discapacidad
            // =========================

            TipoDiscapacidad lesionMano = tipoDiscapacidadService.add(
                    new TipoDiscapacidad(null, "Lesión de mano", "Todo tipo de lesiones de mano", null)
            );

            TipoDiscapacidad lesionPierna = tipoDiscapacidadService.add(
                    new TipoDiscapacidad(null, "Lesión de piernas", "Todo tipo de lesiones de piernas", null)
            );


            // =========================
            // 7. Relacionar pacientes con discapacidades
            // =========================

            pacienteDiscapacidadService.add(
                    new PacienteDiscapacidad(null, lesionMano, paciente1)
            );

            pacienteDiscapacidadService.add(
                    new PacienteDiscapacidad(null, lesionMano, paciente2)
            );

            pacienteDiscapacidadService.add(
                    new PacienteDiscapacidad(null, lesionPierna, paciente3)
            );


            // =========================
            // 8. Relacionar fisioterapeutas con discapacidades
            // =========================

            fisioterapeutaDiscapacidadService.add(
                    new FisioterapeutaDiscapacidad(null, fisio1, lesionMano)
            );

            fisioterapeutaDiscapacidadService.add(
                    new FisioterapeutaDiscapacidad(null, fisio2, lesionPierna)
            );

            fisioterapeutaDiscapacidadService.add(
                    new FisioterapeutaDiscapacidad(null, fisio3, lesionMano)
            );

            fisioterapeutaDiscapacidadService.add(
                    new FisioterapeutaDiscapacidad(null, fisio3, lesionPierna)
            );


            // =========================
            // 9. Crear foros
            // =========================

            Foro foroGeneral = foroService.add(
                    new Foro(null, "Foro general", "Espacio general para todos los pacientes", null, null)
            );

            Foro foroMano1 = foroService.add(
                    new Foro(null, "Lesiones de manos", "Consultas generales sobre lesiones de mano", lesionMano, null)
            );

            Foro foroMano2 = foroService.add(
                    new Foro(null, "Lesiones de manos deportivas", "Lesiones de mano relacionadas con actividades deportivas", lesionMano, null)
            );

            Foro foroMano3 = foroService.add(
                    new Foro(null, "Lesiones de manos por accidentes", "Experiencias y dudas sobre lesiones de mano por accidentes", lesionMano, null)
            );

            Foro foroPierna1 = foroService.add(
                    new Foro(null, "Lesiones de piernas", "Consultas generales sobre lesiones de piernas", lesionPierna, null)
            );


            // =========================
            // 10. Crear publicaciones
            // =========================

            Publicacion pub1 = publicacionService.add(
                    new Publicacion(
                            null,
                            "Me lesioné la muñeca",
                            "Siento dolor al mover la muñeca después de una caída.",
                            LocalDate.of(2024, 12, 10),
                            foroMano1,
                            paciente1,
                            null
                    )
            );

            Publicacion pub2 = publicacionService.add(
                    new Publicacion(
                            null,
                            "Dolor después de terapia",
                            "Después de realizar ejercicios siento molestia en la mano.",
                            LocalDate.of(2024, 12, 12),
                            foroMano1,
                            paciente2,
                            null
                    )
            );

            Publicacion pub3 = publicacionService.add(
                    new Publicacion(
                            null,
                            "Ejercicios para recuperar movilidad",
                            "Quisiera saber qué ejercicios ayudan a recuperar movimiento en la mano.",
                            LocalDate.of(2024, 12, 14),
                            foroMano1,
                            paciente1,
                            null
                    )
            );

            Publicacion pub4 = publicacionService.add(
                    new Publicacion(
                            null,
                            "Lesión de mano jugando vóley",
                            "Tuve una lesión durante un partido y quiero saber cómo manejar la recuperación.",
                            LocalDate.of(2024, 12, 11),
                            foroMano2,
                            paciente2,
                            null
                    )
            );

            Publicacion pub5 = publicacionService.add(
                    new Publicacion(
                            null,
                            "Accidente y dolor en los dedos",
                            "Después de un accidente tengo dolor en los dedos de la mano.",
                            LocalDate.of(2024, 12, 13),
                            foroMano3,
                            paciente1,
                            null
                    )
            );

            Publicacion pub6 = publicacionService.add(
                    new Publicacion(
                            null,
                            "Dolor en la pierna al caminar",
                            "Tengo molestias al caminar después de una lesión en la pierna.",
                            LocalDate.of(2024, 12, 9),
                            foroPierna1,
                            paciente3,
                            null
                    )
            );

            Publicacion pub7 = publicacionService.add(
                    new Publicacion(
                            null,
                            "Ejercicios para fortalecer piernas",
                            "Busco recomendaciones para fortalecer la pierna después de terapia.",
                            LocalDate.of(2024, 12, 15),
                            foroPierna1,
                            paciente3,
                            null
                    )
            );


            // =========================
            // 11. Crear comentarios
            // =========================

            comentarioService.add(
                    new Comentario(null, "A mí también me pasó algo similar.", LocalDate.of(2024, 12, 14), pub1, paciente2)
            );

            comentarioService.add(
                    new Comentario(null, "Te recomendaría consultar con tu fisioterapeuta.", LocalDate.of(2024, 12, 15), pub1, paciente1)
            );

            comentarioService.add(
                    new Comentario(null, "Yo usé una muñequera durante algunos días.", LocalDate.of(2024, 12, 16), pub1, paciente2)
            );

            comentarioService.add(
                    new Comentario(null, "Puede ser normal si recién empezaste terapia.", LocalDate.of(2024, 12, 15), pub2, paciente1)
            );

            comentarioService.add(
                    new Comentario(null, "Los ejercicios suaves me ayudaron bastante.", LocalDate.of(2024, 12, 16), pub3, paciente2)
            );

            comentarioService.add(
                    new Comentario(null, "Consulta si puedes hacer ejercicios con pelota terapéutica.", LocalDate.of(2024, 12, 17), pub3, paciente1)
            );

            comentarioService.add(
                    new Comentario(null, "Yo tuve dolor parecido al caminar.", LocalDate.of(2024, 12, 14), pub6, paciente3)
            );

            comentarioService.add(
                    new Comentario(null, "Los ejercicios de fortalecimiento progresivo ayudan bastante.", LocalDate.of(2024, 12, 16), pub7, paciente3)
            );

            comentarioService.add(
                    new Comentario(null, "También es importante respetar los días de descanso.", LocalDate.of(2024, 12, 17), pub7, paciente3)
            );




            // =========================
            // 14. Crear ejercicios
            // =========================

            Ejercicio ejercicio1 = ejercicioService.add(
                    new Ejercicio(
                            null,
                            "Flexión de muñeca",
                            "Movimiento suave de flexión y extensión de la muñeca.",
                            null
                    )
            );

            Ejercicio ejercicio2 = ejercicioService.add(
                    new Ejercicio(
                            null,
                            "Apertura y cierre de mano",
                            "Ejercicio para mejorar movilidad y fuerza de los dedos.",
                            null
                    )
            );

            Ejercicio ejercicio3 = ejercicioService.add(
                    new Ejercicio(
                            null,
                            "Elevación de pierna",
                            "Ejercicio para fortalecer la musculatura de la pierna.",
                            null
                    )
            );

            Ejercicio ejercicio4 = ejercicioService.add(
                    new Ejercicio(
                            null,
                            "Marcha controlada",
                            "Ejercicio de caminata controlada para mejorar estabilidad.",
                            null
                    )
            );


        };
    }

}
