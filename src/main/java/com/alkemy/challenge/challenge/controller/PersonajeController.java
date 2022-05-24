package com.alkemy.challenge.challenge.controller;

import com.alkemy.challenge.challenge.dto.PeliculaDTO;
import com.alkemy.challenge.challenge.dto.PeliculaFiltroDTO;
import com.alkemy.challenge.challenge.dto.PersonajeDTO;
import com.alkemy.challenge.challenge.dto.PersonajeFiltroDTO;
import com.alkemy.challenge.challenge.service.PersonajeService;
import com.alkemy.challenge.challenge.utils.AppConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("personajes")
@RestController
public class PersonajeController { //maneja una solicitud y una respuesta -- no hay logica de negocios aca

    //Response entity => tipo de objeto que nos ayuda a manejar las response o respuestas

    @Autowired //spring me inicializa este servicio y lo puedo usar aca
    private PersonajeService personajeService;


    @GetMapping
    public ResponseEntity<List<PersonajeDTO>> getAll() {
        List<PersonajeDTO> personajes = this.personajeService.getAllPersonajes();
        return ResponseEntity.ok().body(personajes);
    }

    @GetMapping("/filtros")
    public PersonajeFiltroDTO getPersonajesFiltros(
            @RequestParam(value="pageNumber", defaultValue = AppConst.DEFAULT_NUMBER_PAGE, required = false) int pageNumber,
            @RequestParam(value="pageSize", defaultValue = AppConst.DEFAULT_SIZE_PAGE, required = false) int pageSize,
            @RequestParam(value="sortBy", defaultValue = AppConst.DEFAULT_ORDER_BY, required = false) String sortBy,
            @RequestParam(value="sortDir", defaultValue = AppConst.DEFAULT_ORDER_DIR, required = false) String sortDir //direccion -> asc o desc
    ) {
        return personajeService.getPersonajesFiltros(pageNumber, pageSize, sortBy, sortDir);
    }

    @PostMapping
    public ResponseEntity<PersonajeDTO> save(@RequestBody PersonajeDTO personajeDTO) {
        PersonajeDTO personajeGuardado = personajeService.save(personajeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(personajeGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonajeDTO> update(@RequestBody PersonajeDTO dto, @PathVariable Long id) {
        personajeService.update(dto, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personajeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }












}
