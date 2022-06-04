package com.alkemy.challenge.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "generos")//, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<PeliculaEntity> peliculas = new ArrayList<>();

}
