package com.dh.serie.service;

import com.dh.serie.event.NewSerieEventProducer;
import com.dh.serie.model.Chapter;
import com.dh.serie.model.Season;
import com.dh.serie.model.Serie;
import com.dh.serie.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {

    private final SerieRepository serieRepository;
    private final NewSerieEventProducer newSerieEventProducer;
    private Season season;
    private Chapter chapter;

    public SerieService(SerieRepository serieRepository, NewSerieEventProducer newSerieEventProducer) {
        this.serieRepository = serieRepository;
        this.newSerieEventProducer = newSerieEventProducer;
    }

    public void save(Serie serie) {
        serieRepository.save(serie);
        newSerieEventProducer.execute(serie);
    }


    public List<Serie> getAll() {
        return serieRepository.findAll();
    }

    public Serie getById(Long id) {
        return serieRepository.findById(id).orElse(null);
    }

    public List<Serie> findByGenre(String genre) { return serieRepository.findByGenre(genre); }

    public void deleteById(Long id) {
        serieRepository.deleteById(id);
    }

    public void update(Serie serie) {
        if (serieRepository.existsById(serie.getSerieId())) {
            serieRepository.save(serie);
        }
    }

    public void addSerie(Long serieId, Long seasonId, Long chapterId) throws Exception {
        var serie = serieRepository.findById(serieId);
        if (serie.isPresent()) {
            var result = season.getSeasonId();
            var result2 = chapter.getChapterId();

            if (result == null || result2 == null) {
                throw new Exception("Season or chapter not found");
            }

            serie.get().getSeasons().add(new Season(null, season.getSeasonNumber(), season.getChapters(), serie.get()));
            season.getChapters().add(new Chapter(null, chapter.getName(), chapter.getGenre(), chapter.getUrlStream(), season));

            serieRepository.save(serie.get());
            newSerieEventProducer.execute(serie.get());

        } else {
            throw new Exception("Serie not found");
        }
    }
}
