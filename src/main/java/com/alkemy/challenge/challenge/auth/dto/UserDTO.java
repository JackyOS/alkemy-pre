package com.alkemy.challenge.challenge.auth.dto;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class UserDTO {

    //valido el mail y el password
    @Email(message="Username must be an email")
    private String username;
    @Size(min=8) //minimo 8 caracteres
    private String password;

}
