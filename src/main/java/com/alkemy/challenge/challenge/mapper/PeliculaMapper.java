package com.alkemy.challenge.challenge.mapper;
/*
import com.alkemy.challenge.challenge.dto.PeliculaBasicDTO;
import com.alkemy.challenge.challenge.dto.PeliculaDTO;
import com.alkemy.challenge.challenge.dto.PersonajeDTO;
import com.alkemy.challenge.challenge.entity.PeliculaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component //le digo que simplemente es un componente generico. Me va a permitir inyectarlo en la clase
public class PeliculaMapper {

    @Autowired
    private PersonajeMapper personajeMapper;


                        //pelicula dto a entidad
    public PeliculaEntity peliculaDTO2Entity(PeliculaDTO dto){
        PeliculaEntity peliculaEntity = new PeliculaEntity();
        //no tengo id, solo cargo los datos de la pelicula
        peliculaEntity.setImagen(dto.getImagen());
        peliculaEntity.setTitulo(dto.getTitulo());
        peliculaEntity.setCalificacion(dto.getCalificacion());

        peliculaEntity.setFechaCreacion(this.string2LocalDate(dto.getFechaCreacion()));

        //peliculaEntity.setPersonajes(dto.getPersonajes());
        //peliculaEntity.setGeneros(dto.getGeneros());
        return peliculaEntity;
    }


    //pelicula entidad a dto
    public PeliculaDTO peliculaEntity2DTO(PeliculaEntity entity, boolean loadPersonajes){
        PeliculaDTO dto = new PeliculaDTO();
        dto.setId(entity.getId()); //ahora recibo la id de la base de dato
        dto.setImagen(entity.getImagen());
        dto.setTitulo(entity.getTitulo());
        dto.setFechaCreacion(entity.getFechaCreacion().toString());
        dto.setCalificacion(entity.getCalificacion());

        if(loadPersonajes){
            List<PersonajeDTO> personajesDTO = personajeMapper.personajeEntityList2DTOList(entity.getPersonajes(), false);
            dto.setPersonajes(personajesDTO);
        }

        //dto.setGeneros(entity.getGeneros());
        return dto;
    }


    public List<PeliculaEntity> peliculaDTOList2EntityList(List<PeliculaDTO> dtos){
        List<PeliculaEntity> entities = new ArrayList<>();
        for(PeliculaDTO dto : dtos){
            entities.add(this.peliculaDTO2Entity(dto));
        }
        return entities;
    }



    //tengo una lista de entidades y la paso a dto
    public List<PeliculaDTO> peliculaEntityList2DTOList(List<PeliculaEntity> entities, boolean loadPersonajes){
        List<PeliculaDTO> dtos = new ArrayList<>();

        //recorro las entidades
        for(PeliculaEntity entity : entities){
            //paso cada entidad a dto y la agrego a la lista de dtos
            dtos.add(this.peliculaEntity2DTO(entity, loadPersonajes));
        }

        //retorno los dtos
        return dtos;
    }



    public List<PeliculaBasicDTO> peliculaEntityList2BasicDTOList(List<PeliculaEntity> entities) {
        List<PeliculaBasicDTO> dtos = new ArrayList<>();
        PeliculaBasicDTO peliculaBasica;

        //recorro las entidades
        for(PeliculaEntity entity : entities){
            peliculaBasica = new PeliculaBasicDTO();
            peliculaBasica.setId(entity.getId());
            peliculaBasica.setImagen(entity.getImagen());
            peliculaBasica.setTitulo(entity.getTitulo());
            peliculaBasica.setCalificacion(entity.getCalificacion());
            peliculaBasica.setFechaCreacion(entity.getFechaCreacion().toString());

            //paso cada entidad a dto y la agrego a la lista de dtos
            dtos.add(peliculaBasica);
        }

        //retorno los dtos
        return dtos;
    }


    //paso la fecha que está en formato string a tipo localdate
    private LocalDate string2LocalDate(String stringDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(stringDate, formatter);
        return date;
    }
}

 */
