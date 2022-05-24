package com.alkemy.challenge.challenge.Repository;

import com.alkemy.challenge.challenge.entity.PersonajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonajeRepo extends JpaRepository<PersonajeEntity, Long> {


}
