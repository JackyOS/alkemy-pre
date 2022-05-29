package com.alkemy.challenge.challenge.service;

import com.alkemy.challenge.challenge.dto.PeliculaBasicDTO;
import com.alkemy.challenge.challenge.dto.PeliculaDTO;
import com.alkemy.challenge.challenge.dto.PeliculaFiltroDTO;

import java.util.List;

public interface PeliculaService {

    List<PeliculaBasicDTO> getAllBasicPeliculas();

    PeliculaDTO getDetailsById(Long id);

    PeliculaFiltroDTO getPeliculasFiltros(int pageNumber, int pageSize, String sortBy, String sortDir);

    PeliculaDTO save(PeliculaDTO dto);

    PeliculaDTO update(PeliculaDTO dto, Long id);

    void delete(Long id);

    void addPersonaje(Long id, Long idPersonaje);

    void removePersonaje(Long id, Long idPersonaje);

    void addGenero(Long id, Long idGenero);

    public void removeGenero(Long id, Long idGenero);
}
