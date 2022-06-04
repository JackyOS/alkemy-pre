package com.alkemy.challenge.challenge.controller;

import com.alkemy.challenge.challenge.dto.GeneroDTO;
import com.alkemy.challenge.challenge.dto.PersonajeDTO;
import com.alkemy.challenge.challenge.service.GeneroService;
import com.alkemy.challenge.challenge.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("generos")
@RestController
public class GeneroController { //maneja una solicitud y una respuesta -- no hay logica de negocios aca

    //Response entity => tipo de objeto que nos ayuda a manejar las response o respuestas

    @Autowired //spring me inicializa este servicio y lo puedo usar aca
    private GeneroService generoService;

    @GetMapping
    public ResponseEntity<List<GeneroDTO>> getAll() {
        List<GeneroDTO> generos = this.generoService.getAllGeneros();
        return ResponseEntity.ok().body(generos);
    }

    @PostMapping
    public ResponseEntity<GeneroDTO> save(@RequestBody GeneroDTO generoDTO) {
        GeneroDTO generoGuardado = generoService.save(generoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(generoGuardado); //respondemos con http creado
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneroDTO> update(@RequestBody GeneroDTO dto, @PathVariable Long id) {
        generoService.update(dto, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        generoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
