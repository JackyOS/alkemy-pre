package com.alkemy.challenge.challenge.mapper;
/*
import com.alkemy.challenge.challenge.dto.PeliculaDTO;
import com.alkemy.challenge.challenge.dto.PersonajeDTO;
import com.alkemy.challenge.challenge.entity.PeliculaEntity;
import com.alkemy.challenge.challenge.entity.PersonajeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;



@Component //le digo que simplemente es un componente generico. Me va a permitir inyectarlo en la clase
public class PersonajeMapper {


    @Autowired
    private PeliculaMapper peliculaMapper;

    //pelicula dto a entidad
    public PersonajeEntity personajeDTO2Entity(PersonajeDTO dto){
        PersonajeEntity peliculaEntity = new PersonajeEntity();
        //no tengo id, solo cargo los datos de la pelicula
        peliculaEntity.setImagen(dto.getImagen());
        peliculaEntity.setNombre(dto.getNombre());
        peliculaEntity.setPeso(dto.getPeso());
        peliculaEntity.setEdad(dto.getEdad());
        peliculaEntity.setHistoria(dto.getHistoria());

        List<PeliculaEntity> peliculas = peliculaMapper.peliculaDTOList2EntityList(dto.getPeliculas());
        peliculaEntity.setPeliculas(peliculas);

        return peliculaEntity;
    }


    //pelicula entidad a dto
    public PersonajeDTO personajeEntity2DTO(PersonajeEntity entity, boolean loadPeliculas){
        PersonajeDTO dto = new PersonajeDTO();
        dto.setId(entity.getId()); //ahora recibo la id de la base de dato
        dto.setImagen(entity.getImagen());
        dto.setNombre(entity.getNombre());
        dto.setEdad(entity.getEdad());
        dto.setPeso(entity.getPeso());
        dto.setHistoria(entity.getHistoria());


        if(loadPeliculas){
            List<PeliculaDTO> peliculasDTO = peliculaMapper.peliculaEntityList2DTOList(entity.getPeliculas(), false);
            dto.setPeliculas(peliculasDTO);
        }

        return dto;
    }



    //tengo una lista de entidades y la paso a dto
    public List<PersonajeDTO> personajeEntityList2DTOList(List<PersonajeEntity> entities, boolean loadPeliculas){
        List<PersonajeDTO> dtos = new ArrayList<>();

        if(loadPeliculas) {

            //recorro las entidades
            for (PersonajeEntity entity : entities) {
                //paso cada entidad a dto y la agrego a la lista de dtos
                dtos.add(this.personajeEntity2DTO(entity, loadPeliculas));
            }
        }
        //retorno los dtos
        return dtos;
    }


    //tengo una lista de entidades y la paso a dto
    public List<PersonajeEntity> personajeDTOList2EntityList(List<PersonajeDTO> dtos){
        List<PersonajeEntity> entities = new ArrayList<>();

        //recorro las entidades
        for (PersonajeDTO dto : dtos) {
            //paso cada entidad a dto y la agrego a la lista de dtos
            entities.add(this.personajeDTO2Entity(dto));
        }

        //retorno los dtos
        return entities;
    }


}
*/