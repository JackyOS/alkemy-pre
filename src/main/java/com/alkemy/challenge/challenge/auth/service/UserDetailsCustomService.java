package com.alkemy.challenge.challenge.auth.service;

import com.alkemy.challenge.challenge.auth.dto.UserDTO;
import com.alkemy.challenge.challenge.auth.entity.UserEntity;
import com.alkemy.challenge.challenge.auth.repository.UserRepository;
import com.alkemy.challenge.challenge.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service               // UserDetailsService => es el default de spring security
public class UserDetailsCustomService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //como busco al usuario, en la Base de Datos, cuando me llegue? => por el username
        UserEntity userEntity = userRepository.findByUsername(username);

        //si no encontró al usuario, lanzo una excepcion
        if(userEntity == null){
            throw new UsernameNotFoundException("Username or password not found");
        }

        //si encontró al usuarioEntity, creo un User --de spring security-- y le paso el username, la password y los roles (pero no lo vemos en este proyecto, por eso le paso la lista vacia)
        return new User(userEntity.getUsername(), userEntity.getPassword(), Collections.emptyList());
        //este user lo manejamos en el filtro (jwt...)
    }


    //guarda el usuario en la base de datos
    public boolean save(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(userDTO.getPassword());
        userEntity = this.userRepository.save(userEntity);

        //envio de mail al guardar el usuario
        if(userEntity != null){ //si el usuario no es nulo, le envio el mail
           emailService.sendWelcomeEmailTo(userEntity.getUsername());
        }

        return userEntity != null;
    }


}
