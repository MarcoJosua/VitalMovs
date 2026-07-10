package com.vitalmovs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    private static final String[] AUTH_WHITELIST ={

            // -- Swagger
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-resources/**",


            // -- Login
            "/vitalmovs/users/login/**",

            // -- Registro de nuevo usuarios
            "/vitalmovs/users/register/**",

    };


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*

    1. Cuales van a ser los Request(pedidos) que seran evaluados para saber si el usuario tiene permisos sobre estos request
        a. AnyRequest -> Todos los pedidos
        b. RequestMatcher -> Se evalua solo los que coincidan con las rutas especificadas
        c. RequestMatcher + HttpMethod -> Se evalua solo los que coincidan con las rutas especificadas y con el metodo Http (GET, POST, etc.)

    2. Cual es la regla de autorizacion que se va a aplicar sobre estos Request(pedidos)
        a. permitAll()
        b. denyAll()
        c. hasAnyAuthority()
        d. hasAuthority()
        e. hasRole()
        f. hasAnyRole()
        g. SpEL -> Spring Expression Language
        h. authenticated()

     */


    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        http.cors(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(

                (auth) -> auth

                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        //.anyRequest().permitAll()

                        //Authorization Foro
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/foros/{pacienteId}").hasAnyAuthority("ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/foros/**").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/vitalmovs/foros/**").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST,"/vitalmovs/foros/**").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/vitalmovs/foros/**").hasAnyAuthority("ROLE_ADMIN")

                         //Authorization Publicacion
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/publicaciones").hasAnyAuthority("ROLE_ADMIN","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/publicaciones/{foroId}").hasAnyAuthority("ROLE_ADMIN","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/publicaciones/relevancia/{foroId}").hasAnyAuthority("ROLE_ADMIN","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.PUT,"/vitalmovs/publicaciones/**").hasAnyAuthority("ROLE_ADMIN","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.POST,"/vitalmovs/publicaciones/**").hasAnyAuthority("ROLE_ADMIN","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.DELETE,"/vitalmovs/publicaciones/**").hasAnyAuthority("ROLE_ADMIN","ROLE_PACIENTE")

                         //Authorization Comentario
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/comentarios/**").hasAnyAuthority("ROLE_ADMIN","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.PUT,"/vitalmovs/comentarios/**").hasAnyAuthority("ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.POST,"/vitalmovs/comentarios/**").hasAnyAuthority("ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.DELETE,"/vitalmovs/comentarios/**").hasAnyAuthority("ROLE_ADMIN","ROLE_PACIENTE")


                        //Authorization Fisioterapeuta
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/fisioterapeuta/buscar").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/fisioterapeuta/especialidad").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/fisioterapeuta/compatibles/paciente/{pacienteId}").hasAnyAuthority("ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/fisioterapeuta/user/{userId}").hasAnyAuthority("ROLE_FISIOTERAPEUTA")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/fisioterapeuta/fisioterapeutas").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST,"/vitalmovs/fisioterapeuta").hasAnyAuthority("ROLE_FISIOTERAPEUTA")
                        .requestMatchers(HttpMethod.PUT,"/vitalmovs/fisioterapeuta").hasAnyAuthority("ROLE_FISIOTERAPEUTA")
                        .requestMatchers(HttpMethod.DELETE, "/vitalmovs/fisioterapeuta/{fisioterapeutaId}").hasAnyAuthority("ROLE_FISIOTERAPEUTA")


                        //Authorization FisioterapeutaDiscapacidad
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/fisioterapeutaDiscapacidad/fisioterapeuta/{fisioterapeutaId}").hasAnyAuthority("ROLE_ADMIN", "ROLE_FISIOTERAPEUTA")
                        .requestMatchers(HttpMethod.POST,"/vitalmovs/fisioterapeutaDiscapacidad").hasAnyAuthority("ROLE_FISIOTERAPEUTA")
                        .requestMatchers(HttpMethod.PUT,"/vitalmovs/fisioterapeutaDiscapacidad").hasAnyAuthority("ROLE_FISIOTERAPEUTA")
                        .requestMatchers(HttpMethod.DELETE,"/vitalmovs/fisioterapeutaDiscapacidad/{fdId}").hasAnyAuthority("ROLE_FISIOTERAPEUTA")

                        //Authorization Paciente
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/paciente/pacientes").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/paciente/user/{userId}").hasAnyAuthority("ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/paciente/buscarNombre/{nombre}").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/paciente/buscarSexo/{sexo}").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/paciente/buscarEdad/{edad}").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/paciente/buscarPorTipo/{tipoId}").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST,"/vitalmovs/paciente").hasAnyAuthority("ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.PUT,"/vitalmovs/paciente").hasAnyAuthority("ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.DELETE,"/vitalmovs/paciente/**").hasAnyAuthority("ROLE_PACIENTE")


                        //Authorization TipoDiscapacidad
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/tipoDiscapacidad/tipos").hasAnyAuthority("ROLE_ADMIN","ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.POST,"/vitalmovs/tipoDiscapacidad").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/vitalmovs/tipoDiscapacidad").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/vitalmovs/tipoDiscapacidad/**").hasAnyAuthority("ROLE_ADMIN")

                        //Authoritazion PacienteDiscapacidad
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/pacienteDiscapacidad/paciente/**").hasAnyAuthority("ROLE_ADMIN","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.POST,"/vitalmovs/pacienteDiscapacidad").hasAnyAuthority("ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.PUT,"/vitalmovs/pacienteDiscapacidad").hasAnyAuthority("ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.DELETE,"/vitalmovs/pacienteDiscapacidad/**").hasAnyAuthority("ROLE_PACIENTE")

                        //Authorization Ejercicio
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/ejercicio").hasAnyAuthority("ROLE_ADMIN","ROLE_FISIOTERAPEUTA")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/ejercicio/buscar/{texto}").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/ejercicio/buscarNative/{nombre}").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST,"/vitalmovs/ejercicio").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/vitalmovs/ejercicio").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/vitalmovs/ejercicio/**").hasAnyAuthority("ROLE_ADMIN")


                        //Authorization PlanEjercicio
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/planEjercicio").hasAnyAuthority("ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/planEjercicio/planOrdenado/{planId}").hasAnyAuthority("ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/planEjercicio/plan/{planId}").hasAnyAuthority("ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/planEjercicio/ejercicio/**").hasAnyAuthority("ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/planEjercicio/repeticiones/**").hasAnyAuthority("ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/planEjercicio/planOrdenado/**").hasAnyAuthority("ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/planEjercicio/orden/**").hasAnyAuthority("ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/planEjercicio/dia/**").hasAnyAuthority("ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.POST,"/vitalmovs/planEjercicio").hasAnyAuthority("ROLE_FISIOTERAPEUTA")
                        .requestMatchers(HttpMethod.PUT,"/vitalmovs/planEjercicio").hasAnyAuthority("ROLE_FISIOTERAPEUTA")
                        .requestMatchers(HttpMethod.DELETE,"/vitalmovs/planEjercicio/**").hasAnyAuthority("ROLE_FISIOTERAPEUTA")

                        //Authorization Asignacion
                        .requestMatchers(HttpMethod.GET, "/vitalmovs/asignaciones").hasAnyAuthority("ROLE_ADMIN","ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET, "/vitalmovs/asignaciones/fisioterapeuta/{userId}").hasAnyAuthority("ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET, "/vitalmovs/asignaciones/paciente/{userId}").hasAnyAuthority("ROLE_FISIOTERAPEUTA")
                        .requestMatchers(HttpMethod.POST, "/vitalmovs/asignaciones").hasAnyAuthority("ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.PUT, "/vitalmovs/asignaciones").hasAnyAuthority("ROLE_PACIENTE","ROLE_FISIOTERAPEUTA")
                        .requestMatchers(HttpMethod.DELETE, "/vitalmovs/asignaciones/**").hasAnyAuthority("ROLE_PACIENTE","ROLE_FISIOTERAPEUTA")

                        //Authorization PlanRehabilitacion
                        .requestMatchers(HttpMethod.GET, "/vitalmovs/planes").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/vitalmovs/planes/usuario/{userId}").hasAnyAuthority("ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET, "/vitalmovs/planes/{planId}").hasAnyAuthority("ROLE_ADMIN","ROLE_FISIOTERAPEUTA")
                        .requestMatchers(HttpMethod.POST, "/vitalmovs/planes").hasAnyAuthority("ROLE_FISIOTERAPEUTA")
                        .requestMatchers(HttpMethod.PUT, "/vitalmovs/planes").hasAnyAuthority("ROLE_FISIOTERAPEUTA")
                        .requestMatchers(HttpMethod.DELETE, "/vitalmovs/planes/{planId}").hasAnyAuthority("ROLE_FISIOTERAPEUTA")

                        //Authorization Estadistica
                        .requestMatchers(HttpMethod.GET, "/vitalmovs/estadistica").hasAnyAuthority("ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET, "/vitalmovs/estadistica/{estadisticaId}").hasAnyAuthority("ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET, "/vitalmovs/estadistica/planEjercicio/{planEjercicioId}").hasAnyAuthority("ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET, "/vitalmovs/estadistica/planRehabilitacion/{planId}").hasAnyAuthority("ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET, "/vitalmovs/estadistica/dashboard/resumen/{planId}").hasAnyAuthority("ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET, "/vitalmovs/estadistica/dashboard/ejercicios/{planId}").hasAnyAuthority("ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.POST, "/vitalmovs/estadistica").hasAnyAuthority("ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.PUT, "/vitalmovs/estadistica").hasAnyAuthority("ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/vitalmovs/estadistica/**").hasAnyAuthority("ROLE_PACIENTE")

                        //Query Method - Asignacion por paciente
                        .requestMatchers(HttpMethod.GET, "/vitalmovs/asignaciones/paciente/**")
                        .hasAnyAuthority("ROLE_ADMIN","ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")

                        //Native Query - Estadistica por plan
                        .requestMatchers(HttpMethod.GET, "/vitalmovs/estadisticas/plan/**")
                        .hasAnyAuthority("ROLE_ADMIN","ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")

                        .requestMatchers(HttpMethod.GET, "/vitalmovs/estadisticas/historial/**")
                        .hasAnyAuthority("ROLE_ADMIN","ROLE_FISIOTERAPEUTA","ROLE_PACIENTE")
            
                        .anyRequest().authenticated()
        );

        http.sessionManagement(
                (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        return http.build();
    }

}
