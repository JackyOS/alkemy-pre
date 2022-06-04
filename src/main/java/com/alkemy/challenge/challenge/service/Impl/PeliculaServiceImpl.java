package com.alkemy.challenge.challenge.service.Impl;

import com.alkemy.challenge.challenge.Repository.PeliculaRepo;
import com.alkemy.challenge.challenge.dto.PeliculaBasicDTO;
import com.alkemy.challenge.challenge.dto.PeliculaDTO;
import com.alkemy.challenge.challenge.dto.PeliculaFiltroDTO;
import com.alkemy.challenge.challenge.entity.GeneroEntity;
import com.alkemy.challenge.challenge.entity.PeliculaEntity;
import com.alkemy.challenge.challenge.entity.PersonajeEntity;

import com.alkemy.challenge.challenge.exeption.ParamNotFound;
import com.alkemy.challenge.challenge.service.GeneroService;
import com.alkemy.challenge.challenge.service.PeliculaService;
import com.alkemy.challenge.challenge.service.PersonajeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeliculaServiceImpl implements PeliculaService {

    @Autowired
    private PeliculaRepo peliculaRepo;

    @Autowired
    private PersonajeService personajeService;

    @Autowired
    private GeneroService generoService;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<PeliculaBasicDTO> getAllBasicPeliculas() {
        List<PeliculaEntity> entities = peliculaRepo.findAll();
        return entities.stream().map(pelicula -> mapperEntity2BasicDTO(pelicula)).collect(Collectors.toList());
    }

    @Override
    public PeliculaDTO getDetailsById(Long id) {
       PeliculaEntity entity = peliculaRepo.findById(id).
               orElseThrow(()-> new ParamNotFound("Id de pelicula no valido"));
       return mapperEntity2DTO(entity);
    }

    @Override
    public PeliculaFiltroDTO getPeliculasFiltros(int pageNumber, int pageSize, String sortBy, String sortDir){
        //orderno, si es asc ordeno ascendente sino ordeno descendente
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<PeliculaEntity> peliculas = peliculaRepo.findAll(pageable);
        List<PeliculaEntity> listaPeliculas = peliculas.getContent();
        List<PeliculaDTO> content = listaPeliculas.stream().map(pelicula -> mapperEntity2DTO(pelicula))
                .collect(Collectors.toList());
        PeliculaFiltroDTO peliculaFiltroDTO = new PeliculaFiltroDTO();
        peliculaFiltroDTO.setContent(content);
        peliculaFiltroDTO.setPageNumber(peliculas.getNumber());
        peliculaFiltroDTO.setPageSize(peliculas.getSize());
        peliculaFiltroDTO.setTotalElements(peliculas.getTotalElements());
        peliculaFiltroDTO.setTotalPages(peliculas.getTotalPages());
        peliculaFiltroDTO.setLast(peliculas.isLast());
        return peliculaFiltroDTO;
    }

    @Override
    public PeliculaDTO save(PeliculaDTO dto) {
        PeliculaEntity entity = mapperDTO2Entity(dto);
        PeliculaEntity peliculaGuardada = peliculaRepo.save(entity);
        return mapperEntity2DTO(peliculaGuardada);
    }

    @Override
    public PeliculaDTO update(PeliculaDTO dto, Long id) {
        PeliculaEntity entity = peliculaRepo.getById(id);
        entity.setTitulo(dto.getTitulo());
        entity.setImagen(dto.getImagen());
        entity.setFechaCreacion(dto.getFechaCreacion());
        entity.setCalificacion(dto.getCalificacion());
        PeliculaEntity peliculaGuardada = peliculaRepo.save(entity);
        return mapperEntity2DTO(peliculaGuardada);
    }

    @Override
    public void delete(Long id) {
        peliculaRepo.deleteById(id);
    }

    @Override
    public void addPersonaje(Long id, Long idPersonaje) {
        PeliculaEntity peliculaEntity = peliculaRepo.getById(id);
        PersonajeEntity personajeEntity = personajeService.getEntityById(idPersonaje);

        peliculaEntity.addPersonaje(personajeEntity);
        peliculaRepo.save(peliculaEntity);
    }
    @Override
    public void removePersonaje(Long id, Long idPersonaje) {
        PeliculaEntity peliculaEntity = peliculaRepo.getById(id);
        PersonajeEntity personajeEntity = personajeService.getEntityById(idPersonaje);

        peliculaEntity.removePersonaje(personajeEntity);
        peliculaRepo.save(peliculaEntity);
    }

    @Override
    public void addGenero(Long id, Long idGenero) {
        PeliculaEntity peliculaEntity = peliculaRepo.getById(id);
        GeneroEntity generoEntity = generoService.getEntityById(idGenero);

        peliculaEntity.addGenero(generoEntity);
        peliculaRepo.save(peliculaEntity);
    }
    @Override
    public void removeGenero(Long id, Long idGenero) {
        PeliculaEntity peliculaEntity = peliculaRepo.getById(id);
        GeneroEntity generoEntity = generoService.getEntityById(idGenero);
        peliculaEntity.removeGenero(generoEntity);
        peliculaRepo.save(peliculaEntity);
    }

    //convierte la entidad a dto
    public PeliculaDTO mapperEntity2DTO(PeliculaEntity entidad){
        return modelMapper.map(entidad, PeliculaDTO.class);
    }

    //convierte la entidad a basic dto
    public PeliculaBasicDTO mapperEntity2BasicDTO(PeliculaEntity entidad){return modelMapper.map(entidad, PeliculaBasicDTO.class);}

    //convierte dto a entidad
    public PeliculaEntity mapperDTO2Entity(PeliculaDTO dto){
        return modelMapper.map(dto, PeliculaEntity.class);
    }


}















    /*
    //paso la fecha que está en formato string a tipo localdate
    private LocalDate string2LocalDate(String stringDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(stringDate, formatter);
        return date;
    }
    */
