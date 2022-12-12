package com.dh.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Serie")
public class SerieEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long serieId;
    private String name;
    private String genre;
    private Integer mgCount;
    private List<Season> seasons = new ArrayList<>();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Season {

        private Long seasonId;
        private Integer seasonNumber;
        private List<Chapter> chapters = new ArrayList<>();

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Chapter {

            private Long chapterId;
            private String name;
            private String genre;
            private String urlStream;

        }
    }
}
