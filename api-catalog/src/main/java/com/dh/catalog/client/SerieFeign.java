package com.dh.catalog.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "api-serie")
public interface SerieFeign {

    @GetMapping("/api/v1/series")
    List<Serie> getAll();

    @GetMapping("/api/v1/series/{genre}")
    List<SerieFeign.Serie> getSerieByGenre(@PathVariable(value = "genre") String genre);

    @Getter
    @Setter
    class Serie {
        private Long serieId;
        private String name;
        private String genre;
        private Integer mgCount = 0;
        private List<SerieFeign.Serie.Season> seasons;

        @Getter
        @Setter
        static class Season {
            private Long musicId;
            private Integer seasonNumber;
            private List<SerieFeign.Serie.Season.Chapter> chapters;

            @Getter
            @Setter
            static class Chapter {
                private Long chapterId;
                private String name;
                private String genre;
                private String urlStream;
            }
        }
    }
}
