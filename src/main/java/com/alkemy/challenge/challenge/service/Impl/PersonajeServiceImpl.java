package com.alkemy.challenge.challenge.service.Impl;

import com.alkemy.challenge.challenge.Repository.PersonajeRepo;
import com.alkemy.challenge.challenge.dto.*;
import com.alkemy.challenge.challenge.entity.PersonajeEntity;
import com.alkemy.challenge.challenge.service.PersonajeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonajeServiceImpl implements PersonajeService {

    @Autowired
    private PersonajeRepo personajeRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<PersonajeDTO> getAllPersonajes() {
        List<PersonajeEntity> entities = personajeRepo.findAll();
        return entities.stream().map(personaje -> mapperEntity2DTO(personaje)).collect(Collectors.toList());
    }

    @Override
    public PersonajeEntity getEntityById(Long id) {
        return personajeRepo.getById(id);
    }

    @Override
    public PersonajeFiltroDTO getPersonajesFiltros(int pageNumber, int pageSize, String sortBy, String sortDir){
        //orderno, si es asc ordeno ascendente sino ordeno descendente
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<PersonajeEntity> personajeEntities = personajeRepo.findAll(pageable);
        List<PersonajeEntity> listPersonajes = personajeEntities.getContent();
        List<PersonajeDTO> content = listPersonajes.stream().map(personaje -> mapperEntity2DTO(personaje))
                .collect(Collectors.toList());
        PersonajeFiltroDTO personajeFiltroDTO = new PersonajeFiltroDTO();
        personajeFiltroDTO.setContent(content);
        personajeFiltroDTO.setPageNumber(personajeEntities.getNumber());
        personajeFiltroDTO.setPageSize(personajeEntities.getSize());
        personajeFiltroDTO.setTotalElements(personajeEntities.getTotalElements());
        personajeFiltroDTO.setTotalPages(personajeEntities.getTotalPages());
        personajeFiltroDTO.setLast(personajeEntities.isLast());
        return personajeFiltroDTO;
    }

    @Override
    public PersonajeDTO save(PersonajeDTO dto) {
        PersonajeEntity entity = mapperDTO2Entity(dto);
        PersonajeEntity nuevaEntity = personajeRepo.save(entity);
        return mapperEntity2DTO(nuevaEntity);
    }

    @Override
    public PersonajeDTO update(PersonajeDTO dto, Long id) {
        PersonajeEntity entity = personajeRepo.getById(id);

        entity.setNombre(dto.getNombre());
        entity.setHistoria(dto.getHistoria());
        entity.setEdad(dto.getEdad());
        entity.setImagen(dto.getImagen());
        entity.setPeso(dto.getPeso());

        PersonajeEntity personajeGuardado = personajeRepo.save(entity);
        PersonajeDTO personajeDTO = mapperEntity2DTO(personajeGuardado);

        return personajeDTO;
    }

    @Override
    public void delete(Long id){
        personajeRepo.deleteById(id);
    }

    //convierte la entidad a dto
    public PersonajeDTO mapperEntity2DTO(PersonajeEntity entity){
        return modelMapper.map(entity, PersonajeDTO.class);
    }

    //convierte dto a entidad
    public PersonajeEntity mapperDTO2Entity(PersonajeDTO dto){
        return modelMapper.map(dto, PersonajeEntity.class);
    }


}
