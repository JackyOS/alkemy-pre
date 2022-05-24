package com.alkemy.challenge.challenge.Repository;


import com.alkemy.challenge.challenge.entity.PeliculaEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepo extends JpaRepository<PeliculaEntity, Long> {

    List<PeliculaEntity> findAll(Specification<PeliculaEntity> spec);

}
