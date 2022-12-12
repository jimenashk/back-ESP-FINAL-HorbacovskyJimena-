package com.dh.catalog.service;

import com.dh.catalog.client.MovieFeign;
import com.dh.catalog.client.SerieFeign;
import com.dh.catalog.controller.GetSugerenciaByPopularidadResponse;
import com.dh.catalog.repository.MovieRepositoryMongo;
import com.dh.catalog.repository.SerieRepositoryMongo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private final Map<String, Integer> popularidadMg;
    private final MovieRepositoryMongo movieRepositoryMongo;
    private final SerieRepositoryMongo serieRepositoryMongo;
    private final MovieFeign movieFeign;
    private final SerieFeign serieFeign;


    public CatalogService(Map<String, Integer> popularidadMg, MovieRepositoryMongo movieRepositoryMongo, SerieRepositoryMongo serieRepositoryMongo, MovieFeign movieFeign, SerieFeign serieFeign) {
        this.popularidadMg = new HashMap<>();
        this.movieRepositoryMongo = movieRepositoryMongo;
        this.serieRepositoryMongo = serieRepositoryMongo;
        this.movieFeign = movieFeign;
        this.serieFeign = serieFeign;
        popularidadMg.put("MUY_POPULAR", 5);
        popularidadMg.put("NORMAL", 1);
    }

    public GetSugerenciaByPopularidadResponse getSugerenciaByPopularidadOnline(String popularidad) {
        GetSugerenciaByPopularidadResponse response = new GetSugerenciaByPopularidadResponse();
        findAllMoviesByPopularidad(popularidad, response);
        findAllSeriesByPopularidad(popularidad, response);
        return response;
    }

    @Retry(name = "retryMovie")
    @CircuitBreaker(name = "clientMovie", fallbackMethod = "findAllMoviesFallBack")
    private void findAllMoviesByPopularidad(String popularidad, GetSugerenciaByPopularidadResponse response) {
        var moviesFilter = movieFeign.getAll().stream().filter(movie -> movie.getMgCount() >= popularidadMg.get(popularidad)).collect(Collectors.toList());
        response.setMovies(moviesFilter.stream().map(movie -> {
            GetSugerenciaByPopularidadResponse.MovieDto movieResponse = new GetSugerenciaByPopularidadResponse.MovieDto();
            BeanUtils.copyProperties(movie, movieResponse);
            return movieResponse;
        }).collect(Collectors.toList()));
    }

    private void findAllMoviesFallBack(String popularidad, GetSugerenciaByPopularidadResponse response, Throwable t) {
        var moviesFilter = movieRepositoryMongo.findAll().stream().filter(movie -> movie.getMgCount() >= popularidadMg.get(popularidad)).collect(Collectors.toList());
        response.setMovies(moviesFilter.stream().map(movie -> {
            GetSugerenciaByPopularidadResponse.MovieDto movieResponse = new GetSugerenciaByPopularidadResponse.MovieDto();
            BeanUtils.copyProperties(movie, movieResponse);
            return movieResponse;
        }).collect(Collectors.toList()));
    }

    @Retry(name = "retrySerie")
    @CircuitBreaker(name = "clientSerie", fallbackMethod = "findAllSeriesFallBack")
    private void findAllSeriesByPopularidad(String popularidad, GetSugerenciaByPopularidadResponse response) {
        var seriesFilter = serieFeign.getAll().stream().filter(serie -> serie.getMgCount() >= popularidadMg.get(popularidad)).collect(Collectors.toList());
        response.setSeries(seriesFilter.stream().map(serie -> {
            GetSugerenciaByPopularidadResponse.SerieDto serieResponse = new GetSugerenciaByPopularidadResponse.SerieDto();
            BeanUtils.copyProperties(serie, serieResponse);
            return serieResponse;
        }).collect(Collectors.toList()));
    }

    public void findAllSeriesFallBack(String popularidad, GetSugerenciaByPopularidadResponse response, Throwable t) {
        var seriesFilter = serieRepositoryMongo.findAll().stream().filter(serie -> serie.getMgCount() >= popularidadMg.get(popularidad)).collect(Collectors.toList());
        response.setSeries(seriesFilter.stream().map(serie -> {
            GetSugerenciaByPopularidadResponse.SerieDto serieResponse = new GetSugerenciaByPopularidadResponse.SerieDto();
            BeanUtils.copyProperties(serie, serieResponse);
            return serieResponse;
        }).collect(Collectors.toList()));
    }

    public GetSugerenciaByPopularidadResponse getSugerenciaByPopularidadOffline(String popularidad) {
        GetSugerenciaByPopularidadResponse response = new GetSugerenciaByPopularidadResponse();

        var moviesFilter = movieRepositoryMongo.findAll().stream().filter(movie -> movie.getMgCount() >= popularidadMg.get(popularidad)).collect(Collectors.toList());
        response.setMovies(moviesFilter.stream().map(movie -> {
            GetSugerenciaByPopularidadResponse.MovieDto movieResponse = new GetSugerenciaByPopularidadResponse.MovieDto();
            BeanUtils.copyProperties(movie, movieResponse);
            return movieResponse;
        }).collect(Collectors.toList()));

        var seriesFilter = serieRepositoryMongo.findAll().stream().filter(serie -> serie.getMgCount() >= popularidadMg.get(popularidad)).collect(Collectors.toList());
        response.setSeries(seriesFilter.stream().map(serie -> {
            GetSugerenciaByPopularidadResponse.SerieDto serieResponse = new GetSugerenciaByPopularidadResponse.SerieDto();
            BeanUtils.copyProperties(serie, serieResponse);
            return serieResponse;
        }).collect(Collectors.toList()));

        return response;
    }
}
