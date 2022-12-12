package com.dh.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Movie")
public class MovieEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long movieId;
    private String name;
    private String genre;
    private String urlStream;
    private Integer mgCount;
}
