package com.dh.serie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "serie")
public class Serie implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serie_id", unique = true, nullable = false)
    private Long serieId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "genre", nullable = false, length = 50)
    private String genre;

    @Column(name = "mg_count", nullable = false, columnDefinition = "integer default 0")
    private Integer mgCount = 0;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Season> seasons = new ArrayList<>();
}
