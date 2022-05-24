package com.alkemy.challenge.challenge.dto;

import com.alkemy.challenge.challenge.entity.PeliculaEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@Setter
@Getter
public class GeneroDTO {

    private Long id;
    private String nombre;
    private String imagen;
    private List<PeliculaDTO> peliculas;
}
