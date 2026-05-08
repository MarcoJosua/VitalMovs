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
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/foros/{pacienteId}").hasAnyAuthority("ROLE_ADMIN","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/foros/**").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/vitalmovs/foros/**").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST,"/vitalmovs/foros/**").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/vitalmovs/foros/**").hasAnyAuthority("ROLE_ADMIN")
                         //Authorization Publicacion
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/foros/publicaciones/relevancia/{foroId}").hasAnyAuthority("ROLE_ADMIN","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/publicaciones/{foroId}").hasAnyAuthority("ROLE_ADMIN","ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.PUT,"/vitalmovs/publicaciones/**").hasAnyAuthority("ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.POST,"/vitalmovs/publicaciones/**").hasAnyAuthority("ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.DELETE,"/vitalmovs/publicaciones/**").hasAnyAuthority("ROLE_PACIENTE")
                         //Authorization Comentario
                        .requestMatchers(HttpMethod.GET,"/vitalmovs/comentarios/**").hasAnyAuthority("ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.PUT,"/vitalmovs/comentarios/**").hasAnyAuthority("ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.POST,"/vitalmovs/comentarios/**").hasAnyAuthority("ROLE_PACIENTE")
                        .requestMatchers(HttpMethod.DELETE,"/vitalmovs/comentarios/**").hasAnyAuthority("ROLE_PACIENTE")

                        .anyRequest().authenticated()

        );

        http.sessionManagement(
                (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        return http.build();
    }

}
