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
import java.util.Optional;
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

        PeliculaEntity entity = new PeliculaEntity();

        entity.setTitulo(dto.getTitulo());
        entity.setCalificacion(dto.getCalificacion());
        entity.setImagen(dto.getImagen());
        entity.setFechaCreacion(this.string2LocalDate(dto.getFechaCreacion()));

        PeliculaEntity peliculaGuardada = peliculaRepo.save(entity);
        PeliculaDTO peliculaDTO = mapperEntity2DTO(peliculaGuardada);

        //PeliculaEntity entity = mapperDTO2Entity(dto);
        //PeliculaEntity peliculaGuardada = peliculaRepo.save(entity);
        //PeliculaDTO peliculaDTO = mapperEntity2DTO(peliculaGuardada);

        return peliculaDTO;
    }


    @Override
    public PeliculaDTO update(PeliculaDTO dto, Long id) {
        PeliculaEntity entity = peliculaRepo.getById(id);

        entity.setTitulo(dto.getTitulo());
        entity.setCalificacion(dto.getCalificacion());
        entity.setImagen(dto.getImagen());
        entity.setFechaCreacion(this.string2LocalDate(dto.getFechaCreacion()));

        PeliculaEntity peliculaGuardada = peliculaRepo.save(entity);
        PeliculaDTO peliculaDTO = mapperEntity2DTO(peliculaGuardada);

        return peliculaDTO;
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


    //convierte la entidad a dto
    public PeliculaDTO mapperEntity2DTO(PeliculaEntity entidad){
        return modelMapper.map(entidad, PeliculaDTO.class);
    }

    //convierte la entidad a basic dto
    public PeliculaBasicDTO mapperEntity2BasicDTO(PeliculaEntity entidad){
        return modelMapper.map(entidad, PeliculaBasicDTO.class);
    }

    //convierte dto a entidad
    public PeliculaEntity mapperDTO2Entity(PeliculaDTO dto){
        return modelMapper.map(dto, PeliculaEntity.class);
    }


    //paso la fecha que está en formato string a tipo localdate
    private LocalDate string2LocalDate(String stringDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(stringDate, formatter);
        return date;
    }















    /*
    @Override
    public List<PeliculaDTO> getByFilters(String titulo, Long idGenero, String order) {
        PeliculaFiltroDTO peliculaFiltroDTO = new PeliculaFiltroDTO(titulo,idGenero,order);
        List<PeliculaEntity> entities = this.peliculaRepo.findAll();
        List<PeliculaDTO> dtos = this.mapperEntity2DTO(entities);
        return dtos;
    }*/




    //@Autowired //spring se encarga de inicializarlo
    //private PeliculaMapper peliculaMapper;


    /*
    @Override
    public List<PeliculaDTO> getAllPeliculas() {
        //busco las entidades en la base de datos
        List<PeliculaEntity> entities = peliculaRepo.findAll();

        //paso la lista de entidades a lista de dto
        List<PeliculaDTO> result = peliculaMapper.peliculaEntityList2DTOList(entities);

        //retorno los dtos
        return result;
    }


    @Override
    public List<PeliculaBasicDTO> getAllBasicPeliculas() {
        List<PeliculaEntity> entities = peliculaRepo.findAll();
        List<PeliculaBasicDTO> result = peliculaMapper.peliculaEntityList2BasicDTOList(entities);
        return result;
    }

    @Override
    public PeliculaDTO getDetailsById(Long id) {
        PeliculaEntity entity = peliculaRepo.findById(id).orElseThrow();
        PeliculaDTO dto = peliculaMapper.peliculaEntity2DTO(entity, true);
        return dto;
    }


    @Override
    public List<PeliculaDTO> getByFilters(String titulo, String order) {
        return null;
    }

    public PeliculaDTO save(PeliculaDTO dto){
        System.out.println("Guardar Pelicula");
        //TODO: guardar PELICULA

        //paso de dto a entidad
        PeliculaEntity entity = peliculaMapper.peliculaDTO2Entity(dto);

        //guardo la entidad en la base de datos
        PeliculaEntity entitySaved = peliculaRepo.save(entity); //me devuelve la entidad guardada

        //paso la entidad guardada a dto para pasarlo al controller
        PeliculaDTO result = peliculaMapper.peliculaEntity2DTO(entitySaved, false);

        //devuelvo el dto
        return result;
    }



    @Override
    public PeliculaDTO update(Long id) {
        PeliculaEntity entity = peliculaRepo.findById(id).orElseThrow();
        PeliculaEntity nuevaEntity = peliculaRepo.save(entity);
        PeliculaDTO dto = peliculaMapper.peliculaEntity2DTO(nuevaEntity, false);
        return dto;
    }


    @Override
    public void delete(Long id) {
        peliculaRepo.deleteById(id);
    }


    @Override
    public void addPersonaje(Long id, Long idPersonaje) {
        PeliculaEntity entity = peliculaRepo.getById(id);
        entity.getPersonajes().size();
        PersonajeEntity personajeEntity = this.personajeService.getEntityById(idPersonaje);
        entity.addPersonaje(personajeEntity);
        this.peliculaRepo.save(entity);
    }

    @Override
    public void removePersonaje(Long id, Long idPersonaje) {
        PeliculaEntity entity = peliculaRepo.getById(id);
        entity.getPersonajes().size();
        PersonajeEntity personajeEntity = this.personajeService.getEntityById(idPersonaje);
        entity.removePersonaje(personajeEntity);
        this.peliculaRepo.save(entity);
    }


*/

}
