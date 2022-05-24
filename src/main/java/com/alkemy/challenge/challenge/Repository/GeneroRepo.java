package com.alkemy.challenge.challenge.Repository;

import com.alkemy.challenge.challenge.entity.GeneroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroRepo extends JpaRepository<GeneroEntity, Long> {


}
