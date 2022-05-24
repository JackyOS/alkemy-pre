package com.alkemy.challenge.challenge.dto;

import com.alkemy.challenge.challenge.entity.PeliculaEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@Setter
@Getter
public class PersonajeDTO {

    private Long id;
    private String imagen;
    private String nombre;
    private int edad;
    private int peso;
    private String historia;
    private List<PeliculaBasicDTO> peliculas;
}
