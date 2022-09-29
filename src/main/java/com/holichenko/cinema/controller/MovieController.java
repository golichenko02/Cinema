package com.holichenko.cinema.controller;


import com.holichenko.cinema.domain.dto.MovieDTO;
import com.holichenko.cinema.domain.dto.MovieSearchCriteria;
import com.holichenko.cinema.domain.mapper.MovieMapper;
import com.holichenko.cinema.service.movie.IMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final IMovieService movieService;
    private final MovieMapper movieMapper;

    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@Valid @RequestBody MovieDTO movieDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.createMovie(movieMapper.mapToEntity(movieDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@Valid @RequestBody MovieDTO movieDTO, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(movieService.updateMovie(movieMapper.mapToEntity(movieDTO), id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findById(id));
    }

    @PostMapping("/all")
    public ResponseEntity<Page<MovieDTO>> findAll(@RequestBody MovieSearchCriteria searchCriteria, Pageable pageable) {
        return ResponseEntity.ok(movieService.findAll(searchCriteria, pageable));
    }
}
