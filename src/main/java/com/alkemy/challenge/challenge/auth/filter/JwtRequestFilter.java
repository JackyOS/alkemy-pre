package com.alkemy.challenge.challenge.auth.filter;

import com.alkemy.challenge.challenge.auth.service.JwtUtils;
import com.alkemy.challenge.challenge.auth.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component                         //ejecuto este filtro cada vez que me llegue una request
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsCustomService userDetailsCustomService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired //manejador de authentificaciones de spring security
    private AuthenticationManager authenticationManager;


    @Override //que debe hacer el filtro cuando se ejecuta?   sobreescribo => doFilterInternal
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //me llega por parametro el request y le pido el header "Authorization"
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        //me fijo si no es nulo y que comience con Bearer
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwt = authorizationHeader.substring(7); //obtenemos el jwt, el token en si, pero sin el "Bearer "
            username = jwtUtils.extractUsername(jwt); //obtenemos el username, jwtUtils lo extrae del token
        }


        //si el username no es nulo y si no está authentificado
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            //obtenemos el user details
            UserDetails userDetails = this.userDetailsCustomService.loadUserByUsername(username);


            //verifico si el token que nos llegó es valido, chequeando el token y el user datails
            if(jwtUtils.validateToken(jwt, userDetails)){

                //si pasamos las validaciones, le decimos al contexto de spring que el usuario esta authenticado

                UsernamePasswordAuthenticationToken authReq =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());

                //creamos la authenticacion y...
                Authentication auth = authenticationManager.authenticate(authReq);

                //Set auth in contex => seteamos la authentificacion al contexto
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
