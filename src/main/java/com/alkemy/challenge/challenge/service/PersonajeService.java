package com.alkemy.challenge.challenge.service;

import com.alkemy.challenge.challenge.dto.PersonajeDTO;
import com.alkemy.challenge.challenge.dto.PersonajeFiltroDTO;
import com.alkemy.challenge.challenge.entity.PersonajeEntity;

import java.util.List;

public interface PersonajeService {

    List<PersonajeDTO> getAllPersonajes();

    PersonajeEntity getEntityById(Long idPersonaje);

    PersonajeFiltroDTO getPersonajesFiltros(int pageNumber, int pageSize, String sortBy, String sortDir);

    PersonajeDTO save(PersonajeDTO dto);

    PersonajeDTO update(PersonajeDTO dto, Long id);

    void delete(Long id);
}
