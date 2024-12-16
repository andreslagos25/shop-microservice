package com.microservices.gateway.config;

import com.microservices.gateway.config.filter.JwtTokenValidator;
import com.microservices.gateway.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;


@Configuration
@EnableWebFlux
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF ya que trabajamos con tokens JWT
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.POST, "/api/auth/**").permitAll() // Permitir el acceso a las rutas de autenticación
                        .pathMatchers("/api/client/**").hasAuthority("CREATE") // Requiere autoridad CREATE para acceder a las rutas de clientes
                        .pathMatchers("/api/profile/**").authenticated()
                        .anyExchange().authenticated() // Cualquier otra ruta requiere autenticación
                )
                .addFilterAfter(new JwtTokenValidator(jwtUtils), SecurityWebFiltersOrder.AUTHENTICATION) // Agregar el filtro JWT
                .httpBasic(Customizer.withDefaults()) // Eliminar autenticación básica si ya no la usas
                .build();
    }
}
