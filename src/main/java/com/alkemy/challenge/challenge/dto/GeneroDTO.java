package com.alkemy.challenge.challenge.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class GeneroDTO {

    private Long id;
    private String nombre;
    private String imagen;
    private List<PeliculaBasicDTO> peliculas;
}
