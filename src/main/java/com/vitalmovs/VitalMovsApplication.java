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
            // 1. Crear autoridades (solo si no existen)
            // =========================

            authorityService.add(new Authority(null, "ROLE_ADMIN", null));
            authorityService.add(new Authority(null, "ROLE_PACIENTE", null));
            authorityService.add(new Authority(null, "ROLE_FISIOTERAPEUTA", null));


            // =========================
            // 2. Crear usuarios y datos semilla (solo si no existen)
            // =========================

                userService.add(new UserDTO(null, "admin", "pass", "ROLE_ADMIN"));

                userService.add(new UserDTO(null, "paciente1@gmail.com", "pass", "ROLE_PACIENTE"));
                userService.add(new UserDTO(null, "paciente2@gmail.com", "pass", "ROLE_PACIENTE"));
                userService.add(new UserDTO(null, "paciente3@gmail.com", "pass", "ROLE_PACIENTE"));

                userService.add(new UserDTO(null, "fisioterapeuta1@gmail.com", "pass", "ROLE_FISIOTERAPEUTA"));
                userService.add(new UserDTO(null, "fisioterapeuta2@gmail.com", "pass", "ROLE_FISIOTERAPEUTA"));
                userService.add(new UserDTO(null, "fisioterapeuta3@gmail.com", "pass", "ROLE_FISIOTERAPEUTA"));


            User userPaciente1 = userService.findByUsername("paciente1@gmail.com");
            User userPaciente2 = userService.findByUsername("paciente2@gmail.com");
            User userPaciente3 = userService.findByUsername("paciente3@gmail.com");

            User userFisio1 = userService.findByUsername("fisioterapeuta1@gmail.com");
            User userFisio2 = userService.findByUsername("fisioterapeuta2@gmail.com");
            User userFisio3 = userService.findByUsername("fisioterapeuta3@gmail.com");


            Paciente paciente1 = pacienteService.add(
                    new Paciente(null, "Karin", "Ugarte", 22, "Femenino", userPaciente1, null)
            );

            Paciente paciente2 = pacienteService.add(
                    new Paciente(null, "Kelly", "Guzman", 24, "Femenino", userPaciente2, null)
            );

            Paciente paciente3 = pacienteService.add(
                    new Paciente(null, "Omar", "Perez", 25, "Masculino", userPaciente3, null)
            );


            Fisioterapeuta fisio1 = fisioterapeutaService.add(
                    new Fisioterapeuta(null, "Cris", "Vargas", "Fisioterapia clínica", userFisio1, null)
            );

            Fisioterapeuta fisio2 = fisioterapeutaService.add(
                    new Fisioterapeuta(null, "Taylor", "Coma", "Fisioterapia deportiva", userFisio2, null)
            );

            Fisioterapeuta fisio3 = fisioterapeutaService.add(
                    new Fisioterapeuta(null, "Van", "Cruz", "Fisioterapia general", userFisio3, null)
            );


            TipoDiscapacidad lesionMano = tipoDiscapacidadService.add(
                    new TipoDiscapacidad(null, "Lesión de mano", "Todo tipo de lesiones de mano", null)
            );

            TipoDiscapacidad lesionPierna = tipoDiscapacidadService.add(
                    new TipoDiscapacidad(null, "Lesión de piernas", "Todo tipo de lesiones de piernas", null)
            );


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

            // =========================
// 15. Crear asignaciones
// =========================

// FISIO 1 - asignaciones aceptadas con plan
            Asignacion asignacion1 = asignacionService.add(
                    new Asignacion(null, "Solicito rehabilitación por lesión de muñeca.", LocalDate.of(2025, 1, 5), "ACEPTADO", paciente1, fisio1, null)
            );

            Asignacion asignacion2 = asignacionService.add(
                    new Asignacion(null, "Necesito recuperar fuerza en la mano derecha.", LocalDate.of(2025, 1, 6), "ACEPTADO", paciente2, fisio1, null)
            );

            Asignacion asignacion3 = asignacionService.add(
                    new Asignacion(null, "Tengo dolor al mover los dedos después de una caída.", LocalDate.of(2025, 1, 7), "ACEPTADO", paciente1, fisio1, null)
            );

// FISIO 1 - aceptadas sin plan, deben aparecer en tu pantalla /planes
            Asignacion asignacion4 = asignacionService.add(
                    new Asignacion(null, "Solicito evaluación para iniciar terapia de muñeca.", LocalDate.of(2025, 1, 10), "ACEPTADO", paciente2, fisio1, null)
            );

            Asignacion asignacion5 = asignacionService.add(
                    new Asignacion(null, "Necesito apoyo para mejorar movilidad de la mano.", LocalDate.of(2025, 1, 11), "ACEPTADO", paciente1, fisio1, null)
            );

// FISIO 1 - pendientes y rechazadas, no deben salir como aceptadas sin plan
            Asignacion asignacion6 = asignacionService.add(
                    new Asignacion(null, "Solicitud pendiente para evaluación inicial.", LocalDate.of(2025, 1, 12), "PENDIENTE", paciente2, fisio1, null)
            );

            Asignacion asignacion7 = asignacionService.add(
                    new Asignacion(null, "Solicitud rechazada por disponibilidad.", LocalDate.of(2025, 1, 13), "RECHAZADO", paciente1, fisio1, null)
            );

// FISIO 2 - asignaciones con plan
            Asignacion asignacion8 = asignacionService.add(
                    new Asignacion(null, "Necesito apoyo para recuperar movilidad de la pierna.", LocalDate.of(2025, 1, 6), "ACEPTADO", paciente3, fisio2, null)
            );

            Asignacion asignacion9 = asignacionService.add(
                    new Asignacion(null, "Busco fortalecer la pierna después de una lesión.", LocalDate.of(2025, 1, 8), "ACEPTADO", paciente3, fisio2, null)
            );

// FISIO 2 - aceptada sin plan
            Asignacion asignacion10 = asignacionService.add(
                    new Asignacion(null, "Deseo iniciar terapia de marcha controlada.", LocalDate.of(2025, 1, 14), "ACEPTADO", paciente3, fisio2, null)
            );

// FISIO 3 - mezcla general
            Asignacion asignacion11 = asignacionService.add(
                    new Asignacion(null, "Necesito una segunda evaluación por dolor de mano.", LocalDate.of(2025, 1, 15), "ACEPTADO", paciente2, fisio3, null)
            );

            Asignacion asignacion12 = asignacionService.add(
                    new Asignacion(null, "Solicitud pendiente para terapia general.", LocalDate.of(2025, 1, 16), "PENDIENTE", paciente3, fisio3, null)
            );


// =========================
// 16. Crear planes de rehabilitación
// =========================

            PlanRehabilitacion plan1 = planRehabilitacionService.add(
                    new PlanRehabilitacion(
                            null,
                            "Plan de rehabilitación de muñeca",
                            "Plan orientado a reducir dolor y mejorar la movilidad de la muñeca.",
                            LocalDate.of(2025, 1, 7),
                            LocalDate.of(2025, 2, 7),
                            "ACTIVO",
                            asignacion1
                    )
            );

            PlanRehabilitacion plan2 = planRehabilitacionService.add(
                    new PlanRehabilitacion(
                            null,
                            "Plan de fortalecimiento de mano",
                            "Plan para recuperar fuerza y coordinación en la mano derecha.",
                            LocalDate.of(2025, 1, 9),
                            LocalDate.of(2025, 2, 9),
                            "ACTIVO",
                            asignacion2
                    )
            );

            PlanRehabilitacion plan3 = planRehabilitacionService.add(
                    new PlanRehabilitacion(
                            null,
                            "Plan de movilidad de dedos",
                            "Plan enfocado en mejorar apertura, cierre y control de los dedos.",
                            LocalDate.of(2025, 1, 10),
                            LocalDate.of(2025, 2, 10),
                            "FINALIZADO",
                            asignacion3
                    )
            );

            PlanRehabilitacion plan4 = planRehabilitacionService.add(
                    new PlanRehabilitacion(
                            null,
                            "Plan de rehabilitación de pierna",
                            "Plan orientado a fortalecer la pierna y mejorar la estabilidad al caminar.",
                            LocalDate.of(2025, 1, 8),
                            LocalDate.of(2025, 2, 8),
                            "ACTIVO",
                            asignacion8
                    )
            );

            PlanRehabilitacion plan5 = planRehabilitacionService.add(
                    new PlanRehabilitacion(
                            null,
                            "Plan de fortalecimiento muscular de pierna",
                            "Plan progresivo para mejorar resistencia y fuerza en miembros inferiores.",
                            LocalDate.of(2025, 1, 12),
                            LocalDate.of(2025, 2, 12),
                            "ACTIVO",
                            asignacion9
                    )
            );

            PlanRehabilitacion plan6 = planRehabilitacionService.add(
                    new PlanRehabilitacion(
                            null,
                            "Plan de recuperación funcional de mano",
                            "Plan para recuperar función básica de la mano y reducir molestias.",
                            LocalDate.of(2025, 1, 17),
                            LocalDate.of(2025, 2, 17),
                            "ACTIVO",
                            asignacion11
                    )
            );


// =========================
// 17. Crear plan ejercicios
// =========================

// Plan 1 - muñeca
            PlanEjercicio pe1 = planEjercicioService.add(
                    new PlanEjercicio(null, 3, 12, 10, "LUNES", 1, plan1, ejercicio1, null)
            );

            PlanEjercicio pe2 = planEjercicioService.add(
                    new PlanEjercicio(null, 2, 10, 8, "LUNES", 2, plan1, ejercicio2, null)
            );

            PlanEjercicio pe3 = planEjercicioService.add(
                    new PlanEjercicio(null, 3, 10, 8, "MIERCOLES", 1, plan1, ejercicio2, null)
            );

            PlanEjercicio pe4 = planEjercicioService.add(
                    new PlanEjercicio(null, 3, 15, 10, "MIERCOLES", 2, plan1, ejercicio1, null)
            );

            PlanEjercicio pe5 = planEjercicioService.add(
                    new PlanEjercicio(null, 2, 15, 10, "VIERNES", 1, plan1, ejercicio1, null)
            );

            PlanEjercicio pe6 = planEjercicioService.add(
                    new PlanEjercicio(null, 2, 12, 9, "VIERNES", 2, plan1, ejercicio2, null)
            );


// Plan 2 - mano
            PlanEjercicio pe7 = planEjercicioService.add(
                    new PlanEjercicio(null, 4, 10, 12, "MARTES", 1, plan2, ejercicio2, null)
            );

            PlanEjercicio pe8 = planEjercicioService.add(
                    new PlanEjercicio(null, 3, 12, 10, "MARTES", 2, plan2, ejercicio1, null)
            );

            PlanEjercicio pe9 = planEjercicioService.add(
                    new PlanEjercicio(null, 3, 12, 10, "JUEVES", 1, plan2, ejercicio1, null)
            );

            PlanEjercicio pe10 = planEjercicioService.add(
                    new PlanEjercicio(null, 2, 15, 8, "JUEVES", 2, plan2, ejercicio2, null)
            );

            PlanEjercicio pe11 = planEjercicioService.add(
                    new PlanEjercicio(null, 3, 10, 9, "VIERNES", 1, plan2, ejercicio1, null)
            );


// Plan 3 - dedos finalizado
            PlanEjercicio pe12 = planEjercicioService.add(
                    new PlanEjercicio(null, 3, 8, 8, "LUNES", 1, plan3, ejercicio2, null)
            );

            PlanEjercicio pe13 = planEjercicioService.add(
                    new PlanEjercicio(null, 2, 10, 7, "LUNES", 2, plan3, ejercicio1, null)
            );

            PlanEjercicio pe14 = planEjercicioService.add(
                    new PlanEjercicio(null, 3, 10, 9, "MIERCOLES", 1, plan3, ejercicio2, null)
            );

            PlanEjercicio pe15 = planEjercicioService.add(
                    new PlanEjercicio(null, 2, 12, 8, "VIERNES", 1, plan3, ejercicio1, null)
            );


// Plan 4 - pierna
            PlanEjercicio pe16 = planEjercicioService.add(
                    new PlanEjercicio(null, 3, 15, 12, "LUNES", 1, plan4, ejercicio3, null)
            );

            PlanEjercicio pe17 = planEjercicioService.add(
                    new PlanEjercicio(null, 2, 12, 10, "LUNES", 2, plan4, ejercicio4, null)
            );

            PlanEjercicio pe18 = planEjercicioService.add(
                    new PlanEjercicio(null, 2, 10, 15, "MIERCOLES", 1, plan4, ejercicio4, null)
            );

            PlanEjercicio pe19 = planEjercicioService.add(
                    new PlanEjercicio(null, 3, 12, 14, "MIERCOLES", 2, plan4, ejercicio3, null)
            );

            PlanEjercicio pe20 = planEjercicioService.add(
                    new PlanEjercicio(null, 3, 12, 14, "VIERNES", 1, plan4, ejercicio3, null)
            );

            PlanEjercicio pe21 = planEjercicioService.add(
                    new PlanEjercicio(null, 2, 10, 12, "VIERNES", 2, plan4, ejercicio4, null)
            );


// Plan 5 - fortalecimiento pierna
            PlanEjercicio pe22 = planEjercicioService.add(
                    new PlanEjercicio(null, 4, 12, 15, "MARTES", 1, plan5, ejercicio3, null)
            );

            PlanEjercicio pe23 = planEjercicioService.add(
                    new PlanEjercicio(null, 3, 10, 12, "MARTES", 2, plan5, ejercicio4, null)
            );

            PlanEjercicio pe24 = planEjercicioService.add(
                    new PlanEjercicio(null, 3, 10, 18, "JUEVES", 1, plan5, ejercicio4, null)
            );

            PlanEjercicio pe25 = planEjercicioService.add(
                    new PlanEjercicio(null, 4, 8, 14, "JUEVES", 2, plan5, ejercicio3, null)
            );

            PlanEjercicio pe26 = planEjercicioService.add(
                    new PlanEjercicio(null, 3, 12, 15, "VIERNES", 1, plan5, ejercicio3, null)
            );


// Plan 6 - mano fisio 3
            PlanEjercicio pe27 = planEjercicioService.add(
                    new PlanEjercicio(null, 3, 12, 10, "LUNES", 1, plan6, ejercicio1, null)
            );

            PlanEjercicio pe28 = planEjercicioService.add(
                    new PlanEjercicio(null, 2, 10, 8, "LUNES", 2, plan6, ejercicio2, null)
            );

            PlanEjercicio pe29 = planEjercicioService.add(
                    new PlanEjercicio(null, 3, 10, 8, "MIERCOLES", 1, plan6, ejercicio2, null)
            );

            PlanEjercicio pe30 = planEjercicioService.add(
                    new PlanEjercicio(null, 3, 12, 10, "MIERCOLES", 2, plan6, ejercicio1, null)
            );

            PlanEjercicio pe31 = planEjercicioService.add(
                    new PlanEjercicio(null, 2, 12, 10, "VIERNES", 1, plan6, ejercicio1, null)
            );

            PlanEjercicio pe32 = planEjercicioService.add(
                    new PlanEjercicio(null, 2, 10, 9, "VIERNES", 2, plan6, ejercicio2, null)
            );

// =========================
// 18. Crear estadísticas
// =========================

// Estadísticas plan 1
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 13), 7, 8, 28, 8, "Me costó completar todas las repeticiones por dolor en la muñeca.", pe1));
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 20), 5, 6, 34, 9, "Sentí menos dolor y pude realizar más repeticiones.", pe1));
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 15), 6, 7, 25, 7, "Tuve dificultad al cerrar la mano completamente.", pe2));
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 22), 4, 5, 29, 8, "La movilidad mejoró durante la sesión.", pe2));

// Estadísticas plan 2
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 16), 6, 7, 30, 10, "Sentí cansancio en los dedos al terminar.", pe4));
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 23), 5, 6, 35, 11, "Pude completar más repeticiones que la semana anterior.", pe4));
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 18), 4, 5, 32, 9, "Ejercicio tolerable, con poca molestia.", pe5));

// Estadísticas plan 3
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 13), 6, 6, 22, 7, "Todavía hay rigidez en los dedos.", pe6));
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 20), 3, 4, 28, 8, "Mejoré el cierre de la mano.", pe6));
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 22), 2, 3, 30, 9, "Pude completar la rutina sin dolor fuerte.", pe7));

// Estadísticas plan 4
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 13), 6, 7, 38, 10, "Sentí cansancio al elevar la pierna.", pe8));
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 20), 4, 5, 45, 12, "Mejoré la resistencia y tuve menos dolor.", pe8));
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 17), 5, 6, 18, 13, "La marcha fue más estable, aunque con dificultad.", pe9));
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 24), 3, 4, 22, 15, "Pude caminar con mayor control.", pe9));

// Estadísticas plan 5
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 21), 5, 6, 40, 14, "Sentí fatiga muscular moderada.", pe11));
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 28), 3, 4, 48, 15, "La fuerza mejoró en comparación con la sesión anterior.", pe11));
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 23), 4, 5, 25, 16, "La marcha controlada fue más fluida.", pe12));

// Estadísticas plan 6
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 24), 6, 7, 26, 8, "Dolor moderado al iniciar la rutina.", pe13));
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 31), 4, 5, 33, 9, "Mejor respuesta al ejercicio de muñeca.", pe13));
            estadisticaService.add(new Estadistica(null, LocalDate.of(2025, 1, 29), 5, 6, 24, 7, "Dificultad leve al abrir y cerrar la mano.", pe14));


        };
    }

}
