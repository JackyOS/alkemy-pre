package com.alkemy.challenge.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "genero")
@Entity
public class GeneroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private String imagen;

    @ManyToMany(mappedBy ="generos", cascade = CascadeType.ALL)
    private Set<PeliculaEntity> peliculas = new HashSet<>();
    //cuando creo un genero, no voy a poder pasar una lista de peliculas para que lo cree

    //agregar y eliminar peliculas
    public void agregarPelicula(PeliculaEntity pelicula) {
        this.peliculas.add(pelicula);
    }

    public void eliminarPelicula(PeliculaEntity pelicula) {
        this.peliculas.remove(pelicula);
    };

}
