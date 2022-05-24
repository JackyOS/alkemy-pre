package com.alkemy.challenge.challenge.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class PeliculaDTO {

    private Long id;
    private String imagen;
    private String titulo;
    private String fechaCreacion;
    private int calificacion; //DE 1 A 5
    private List<GeneroBasicDTO> generos;
    private List<PersonajeDTO> personajes;

}
