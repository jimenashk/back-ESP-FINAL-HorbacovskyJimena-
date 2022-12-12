package com.dh.catalog;

import com.dh.catalog.client.MovieFeign;
import com.dh.catalog.controller.GetSugerenciaByPopularidadResponse;
import io.restassured.http.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpotifyTest extends BaseAPI {

    @Test
    @Tag("functional")
    @DisplayName("Testear toda la aplicacion con api gateway")
    void testingAll() {

        given().
                contentType(ContentType.JSON).
                body(
                        new MovieRequest(1L, "Buscando a Nemo", "Infantil", 10)
                ).
                when().post("/api/v1/musics");

        given().
                contentType(ContentType.JSON).
                body(
                        new SerieRequest(1L, "The Big Bang Theory", "Comedia", 10)
                ).
                when().post("/api/v1/playlists");

        GetSugerenciaByPopularidadResponse responseOnline = given()
                .pathParam("popularidad", "MUY_POPULAR")
                .when().get("/api/v1/catalog/online/{popularidad}")
                .andReturn().body().as(GetSugerenciaByPopularidadResponse.class);

        GetSugerenciaByPopularidadResponse responseOffline = given()
                .pathParam("popularidad", "MUY_POPULAR")
                .when().get("/api/v1/catalog/offline/{popularidad}")
                .andReturn().body().as(GetSugerenciaByPopularidadResponse.class);

        assertEquals(responseOnline.getMovies().size(), 1);
        assertEquals(responseOnline.getSeries().size(), 1);

        assertEquals(responseOffline.getMovies().size(), 1);
        assertEquals(responseOffline.getSeries().size(), 1);

        given()
                .pathParam("id", 158L).
                when().delete("/api/v1/movies/{id}");
        given()
                .pathParam("id", 365L).
                when().delete("/api/v1/series/{id}");
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovieRequest {
        private Long movieId;
        private String name;
        private String genre;
        private Integer mgCount = 0;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SerieRequest {
        private Long serieId;
        private String name;
        private String genre;
        private Integer mgCount = 0;
    }

}
