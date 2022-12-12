package com.dh.movie.service;

import com.dh.movie.event.NewMovieEventProducer;
import com.dh.movie.model.Movie;
import com.dh.movie.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    private final NewMovieEventProducer newMovieEventProducer;

    public MovieService(MovieRepository movieRepository, NewMovieEventProducer newMovieEventProducer) {
        this.movieRepository = movieRepository;
        this.newMovieEventProducer = newMovieEventProducer;
    }

    public void save(Movie movie) {
        movieRepository.save(movie);
        newMovieEventProducer.execute(movie);
    }

    public void update(Movie movie) {
        if (movieRepository.existsById(movie.getMovieId())) {
            movieRepository.save(movie);
        }
    }

    public List<Movie> getAll() { return movieRepository.findAll(); }

    public Movie getById(Long id) { return movieRepository.findById(id).orElse(null); }

    public List<Movie> findByGenre(String genre) { return movieRepository.findByGenre(genre); }

    public void deleteById(Long id) { movieRepository.deleteById(id); }
}
