package com.alkemy.challenge.challenge.dto;

import com.alkemy.challenge.challenge.entity.GeneroEntity;
import com.alkemy.challenge.challenge.entity.PersonajeEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;


@Setter
@Getter
public class PeliculaBasicDTO {

    private Long id;
    private String imagen;
    private String titulo;
    private LocalDate fechaCreacion;
    private int calificacion; //DE 1 A 5

}
