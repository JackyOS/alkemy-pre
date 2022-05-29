package com.alkemy.challenge.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "pelicula")
@Entity
public class PeliculaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imagen;
    private String titulo;

    @Column(name = "fecha_creacion")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate fechaCreacion;

    private int calificacion; //DE 1 A 5

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "pelicula_personaje",
            joinColumns = @JoinColumn(name="pelicula_id"),
            inverseJoinColumns = @JoinColumn(name="personaje_id")
    )
    private List<PersonajeEntity> personajes = new ArrayList<>();
    //cuando creo la pelicula, la creo con la lista de personajes asociados



    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable( //creamos una tabla intermedia que crea una relacion de muchos a muchos entre peliculas y generos
            name = "pelicula_genero",
            joinColumns = @JoinColumn(name="pelicula_id"),
            inverseJoinColumns = @JoinColumn(name="genero_id")
    )
    private List<GeneroEntity> generos = new ArrayList<>();
    //cuando creo la pelicula, la creo con la lista de generos asociados

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final PeliculaEntity other = (PeliculaEntity) o;
        return other.id == this.id;
    }

    public void addGenero(GeneroEntity generoEntity){
        this.generos.add(generoEntity);
    }
    public void removeGenero(GeneroEntity generoEntity){
        this.generos.remove(generoEntity);
    }

    public void addPersonaje(PersonajeEntity personajeEntity){
        this.personajes.add(personajeEntity);
    }
    public void removePersonaje(PersonajeEntity personajeEntity){
        this.personajes.remove(personajeEntity);
    }

}













/*
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "genero_id", insertable = false, updatable = false)
    private GeneroEntity genero; //me traigo el genero entero, obtengo la info del objeto

    @Column(name = "genero_id", nullable = false)
    private Long generoId; //lo uso para guardar y actualizar el id



        @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable( //creamos una tabla intermedia que crea una relacion de muchos a muchos entre peliculas y generos
            name = "pelicula_genero",
            joinColumns = @JoinColumn(name="pelicula_id"),
            inverseJoinColumns = @JoinColumn(name="genero_id")
    )
    private List<GeneroEntity> generos = new ArrayList<>();
    //cuando creo la pelicula, la creo con la lista de generos asociados




    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "genero_id", insertable = false, updatable = false)
    private GeneroEntity genero; //me traigo el genero entero, obtengo la info del objeto

    @Column(name = "genero_id", nullable = false)
    private Long generoId; //lo uso para guardar y actualizar el id


*/

//eager = inicializacion de tipo tipo temprana
//cuando me pida un dato de tipo pelicula, si o si me trae el genero.


//cascade. all => si hago un delete con la pelicula tambien lo hace con el genero asociado

//insertable= false y updetable = false => uso ese dato para traer el genero, para obtener info, no lo voy a modificar ni nada