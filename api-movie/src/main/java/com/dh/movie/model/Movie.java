package com.dh.movie.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie")
public class Movie implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id

    @Column(name = "movie_id", unique = true, nullable = false)
    private Long movieId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "genre", nullable = false, length = 50)
    private String genre;

    @Column(name = "url_stream", nullable = false, length = 250)
    private String urlStream;

    @Column(name = "mg_count", nullable = false, columnDefinition = "integer default 0")
    private Integer mgCount = 0;
}
