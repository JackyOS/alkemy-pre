package com.alkemy.challenge.challenge.service;

import com.alkemy.challenge.challenge.dto.GeneroDTO;
import com.alkemy.challenge.challenge.entity.GeneroEntity;

import java.util.List;

public interface GeneroService {

    GeneroDTO save(GeneroDTO dto);

    List<GeneroDTO> getAllGeneros();

    GeneroDTO update(GeneroDTO dto, Long id);

    GeneroEntity getEntityById(Long id);

    void delete(Long id);

}
