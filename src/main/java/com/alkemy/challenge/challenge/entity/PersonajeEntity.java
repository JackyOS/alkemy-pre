package com.alkemy.challenge.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "personaje")
@Entity
public class PersonajeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imagen;
    private String nombre;
    private int edad;
    private int peso;
    private String historia;

    @ManyToMany(mappedBy ="personajes", cascade = CascadeType.ALL)
    private List<PeliculaEntity> peliculas = new ArrayList<>();
    //cuando creo un personaje, no voy a poder pasar una lista de peliculas para que lo cree

    /*
    //agregar y eliminar peliculas
    public void agregarPelicula(PeliculaEntity pelicula) {
        this.peliculas.add(pelicula);
    }

    public void eliminarPelicula(PeliculaEntity pelicula) {
        this.peliculas.remove(pelicula);
    }

     */
}
