package com.alkemy.challenge.challenge.service.Impl;


import com.alkemy.challenge.challenge.Repository.GeneroRepo;
import com.alkemy.challenge.challenge.dto.GeneroDTO;
import com.alkemy.challenge.challenge.entity.GeneroEntity;
import com.alkemy.challenge.challenge.service.GeneroService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepo generoRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<GeneroDTO> getAllGeneros() {
        List<GeneroEntity> entities = generoRepo.findAll();
        return entities.stream().map(genero -> mapperEntity2DTO(genero)).collect(Collectors.toList());
    }

    @Override
    public GeneroEntity getEntityById(Long id) {
        return generoRepo.getById(id);
    }


    @Override
    public GeneroDTO save(GeneroDTO dto) {
        GeneroEntity entity = mapperDTO2Entity(dto);
        GeneroEntity generoGuardado = generoRepo.save(entity);
        GeneroDTO generoDTO = mapperEntity2DTO(generoGuardado);
        return generoDTO;
    }

    @Override
    public GeneroDTO update(GeneroDTO dto, Long id) {
        GeneroEntity entity = generoRepo.getById(id);

        entity.setImagen(dto.getImagen());
        entity.setNombre(dto.getNombre());

        GeneroEntity generoGuardado = generoRepo.save(entity);
        GeneroDTO generoDTO = mapperEntity2DTO(generoGuardado);

        return generoDTO;
    }

    @Override
    public void delete(Long id) {
        generoRepo.deleteById(id);
    }

    //convierte la entidad a dto
    public GeneroDTO mapperEntity2DTO(GeneroEntity entity){
        return modelMapper.map(entity, GeneroDTO.class);
    }

    //convierte dto a entidad
    public GeneroEntity mapperDTO2Entity(GeneroDTO dto){
        return modelMapper.map(dto, GeneroEntity.class);
    }



}
