package com.alkemy.challenge.challenge.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "genero")
@Entity
public class GeneroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String imagen;

    //@OneToMany(mappedBy ="genero", cascade = CascadeType.ALL)
    //@JsonBackReference //me soluciona el problema de recursion infinita cuando hay relaciones bidireccionales
    @ManyToMany(mappedBy = "generos", cascade = CascadeType.ALL)
    private List<PeliculaEntity> peliculas = new ArrayList<>();
    //cuando creo un genero, no voy a poder pasar una lista de peliculas para que lo cree

}
