package com.microservices.gateway.config.filter;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.microservices.gateway.util.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;

public class JwtTokenValidator implements WebFilter {

    private JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils){
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String jwtToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        Authentication authenticationToken = null;

        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            // Se obtiene el substring de después de los primeros 7 caracteres (para eliminar 'Bearer ')
            jwtToken = jwtToken.substring(7);

            // Llamamos a la clase jwtUtils para validar el token
            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

            // Extraemos el username y los permisos del usuario
            String username = jwtUtils.extractUsername(decodedJWT);
            String stringAuthorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

            // Convertimos los authorities a un formato que Spring Security entienda
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);

            // Creamos un SecurityContext con el usuario autenticado y sus authorities
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            context.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(context);
        }

        // Añadimos el contexto de seguridad al flujo reactivo
        return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authenticationToken));
    }
}
