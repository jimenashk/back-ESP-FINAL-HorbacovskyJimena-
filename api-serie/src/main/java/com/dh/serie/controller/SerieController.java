package com.dh.serie.controller;

import com.dh.serie.model.Serie;
import com.dh.serie.service.SerieService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/series")
public class SerieController {

    private final SerieService serieService;

    public SerieController(SerieService serieService) {
        this.serieService = serieService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Long> create(@RequestBody Serie serie) {
        serieService.save(serie);
        return ResponseEntity.ok(serie.getSerieId());
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity update(@RequestBody Serie serie) {
        serieService.update(serie);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{genre}")
    ResponseEntity<List<Serie>> getSerieByGenre(@PathVariable String genre) {
        return ResponseEntity.ok().body(serieService.findByGenre(genre));
    }

    @PatchMapping("/addSerie")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity addSerie(@RequestBody AddSerieDto addSerieDto) {
        try {
            serieService.addSerie(addSerieDto.getSerieId(), addSerieDto.getSeasonId(), addSerieDto.chapterId);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @Getter
    @Setter
    static class AddSerieDto{
        private Long serieId;
        private Long seasonId;
        private Long chapterId;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<Serie>> getAll() {
        return ResponseEntity.ok(serieService.getAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Serie> getById(@PathVariable Long id) {
        return ResponseEntity.ok(serieService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity delete(@PathVariable Long id) {
        serieService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
