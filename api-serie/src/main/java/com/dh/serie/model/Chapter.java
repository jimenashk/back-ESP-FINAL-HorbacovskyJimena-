package com.dh.serie.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chapter")
public class Chapter implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id", unique = true, nullable = false)
    private Long chapterId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "genre", nullable = false, length = 50)
    private String genre;

    @Column(name = "url_stream", nullable = false, length = 100)
    private String urlStream;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "season_id", nullable = false, referencedColumnName = "season_id", foreignKey = @ForeignKey(name = "fk_chapter_season"))
    @JsonIgnore
    private Season season;
}
