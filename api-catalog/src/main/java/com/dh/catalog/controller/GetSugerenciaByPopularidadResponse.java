package com.dh.catalog.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetSugerenciaByPopularidadResponse {

    private List<GetSugerenciaByPopularidadResponse.MovieDto> movies = new ArrayList<>();
    private List<GetSugerenciaByPopularidadResponse.SerieDto> series = new ArrayList<>();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovieDto{
        private String name;
        private String genre;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SerieDto{
        private String name;
        private String genre;
    }
}
