package com.alkemy.challenge.challenge.auth.controller;

import com.alkemy.challenge.challenge.auth.dto.AuthenticationRequest;
import com.alkemy.challenge.challenge.auth.dto.AuthenticationResponse;
import com.alkemy.challenge.challenge.auth.dto.UserDTO;
import com.alkemy.challenge.challenge.auth.service.JwtUtils;
import com.alkemy.challenge.challenge.auth.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserAuthController { //para registrarno y para hacer el login

    private UserDetailsCustomService userDetailsCustomService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    @Autowired //es lo mismo que poner autowire en cada atributo
    public UserAuthController(UserDetailsCustomService userDetailsCustomService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userDetailsCustomService = userDetailsCustomService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/singup") //registrarse
    public ResponseEntity<AuthenticationResponse> singUp(@Valid @RequestBody UserDTO user) throws Exception {
        this.userDetailsCustomService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping("/singin") //iniciar sesion - Intento autenticarme y busca en la base de datos si el usuario existe
    public ResponseEntity<AuthenticationResponse> singIn(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        //esto se tendria que hacer en la parte de servicio porque usa logica
        UserDetails userDetails;
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            userDetails = (UserDetails) auth.getPrincipal();
        } catch (BadCredentialsException e){
            throw new Exception("Password o username incorrecto", e);
        }
        //Se genera el token con los datos recibidos -userdetails- del inicio de sesion
        //por eso se usa un try - catch para capturar un error, si hay.
        final String jwt= jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }



}
