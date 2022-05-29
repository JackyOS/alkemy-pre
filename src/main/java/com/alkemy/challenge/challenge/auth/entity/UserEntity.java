package com.alkemy.challenge.challenge.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Email
    private String username;
    @Size(min=8)
    private String password;
    private boolean accountNonExpired; //si la cuenta no expiró
    private boolean accountNonLocked; // si la cuenta no fue bloqueada
    private boolean credentialNonExpired; //si las credenciales no expiraron
    private boolean enabled; //si está habilitado o no el usuario


    //Se declara un constructor con valores por defecto para que sea utilizable
     public UserEntity() {
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialNonExpired = true;
        this.enabled = true;

    }
}
