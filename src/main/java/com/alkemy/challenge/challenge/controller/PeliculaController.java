package com.alkemy.challenge.challenge.controller;

import com.alkemy.challenge.challenge.dto.PeliculaBasicDTO;
import com.alkemy.challenge.challenge.dto.PeliculaDTO;
import com.alkemy.challenge.challenge.dto.PeliculaFiltroDTO;
import com.alkemy.challenge.challenge.service.PeliculaService;
import com.alkemy.challenge.challenge.utils.AppConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("peliculas")
@RestController
public class PeliculaController { //maneja una solicitud y una respuesta -- no hay logica de negocios aca


    @Autowired //spring me inicializa este servicio y lo puedo usar aca
    private PeliculaService peliculaService;

    @GetMapping
    public ResponseEntity<List<PeliculaBasicDTO>> getAll() {
        List<PeliculaBasicDTO> peliculas = this.peliculaService.getAllBasicPeliculas();
        return ResponseEntity.ok().body(peliculas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeliculaDTO> getDetailsAll(@PathVariable Long id) {
        PeliculaDTO pelicula = this.peliculaService.getDetailsById(id);
        return ResponseEntity.ok(pelicula);
    }

    @GetMapping("/filtros") //listar peliculas
    public PeliculaFiltroDTO getPeliculasFiltros(
            @RequestParam(value="pageNumber", defaultValue = AppConst.DEFAULT_NUMBER_PAGE, required = false) int pageNumber,
            @RequestParam(value="pageSize", defaultValue = AppConst.DEFAULT_SIZE_PAGE, required = false) int pageSize,
            @RequestParam(value="sortBy", defaultValue = AppConst.DEFAULT_ORDER_BY, required = false) String sortBy,
            @RequestParam(value="sortDir", defaultValue = AppConst.DEFAULT_ORDER_DIR, required = false) String sortDir //direccion -> asc o desc
    ) {
        return peliculaService.getPeliculasFiltros(pageNumber, pageSize, sortBy, sortDir);
    }

    @PostMapping
    public ResponseEntity<PeliculaDTO> save(@RequestBody PeliculaDTO pelicula) {
        PeliculaDTO peliculaGuardada = peliculaService.save(pelicula);
        return ResponseEntity.status(HttpStatus.CREATED).body(peliculaGuardada); //respondemos con http creado
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeliculaDTO> update(@RequestBody PeliculaDTO dto, @PathVariable Long id) {
        PeliculaDTO pelicula = peliculaService.update(dto, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        peliculaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //agregar personaje a la pelicula
    @PostMapping("/{id}/personaje/{idPersonaje}")
    public ResponseEntity<Void> addPersonaje(@PathVariable Long id, @PathVariable Long idPersonaje) {
        peliculaService.addPersonaje(id,idPersonaje);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    //eliminar personaje de la pelicula
    @DeleteMapping("/{id}/personaje/{idPersonaje}")
    public ResponseEntity<Void> removePersonaje(@PathVariable Long id, @PathVariable Long idPersonaje) {
        peliculaService.removePersonaje(id,idPersonaje);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //agregar genero a la pelicula
    @PostMapping("/{id}/genero/{idGenero}")
    public ResponseEntity<Void> addGenero(@PathVariable Long id, @PathVariable Long idGenero) {
        peliculaService.addGenero(id,idGenero);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    //eliminar genero de la pelicula
    @DeleteMapping("/{id}/genero/{idGenero}")
    public ResponseEntity<Void> removeGenero(@PathVariable Long id, @PathVariable Long idGenero) {
        peliculaService.removeGenero(id,idGenero);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
