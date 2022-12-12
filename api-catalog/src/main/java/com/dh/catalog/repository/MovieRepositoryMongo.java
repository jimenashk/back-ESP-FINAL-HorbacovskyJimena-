package com.dh.catalog.repository;

import com.dh.catalog.model.MovieEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepositoryMongo extends MongoRepository<MovieEntity, Long> {

    List<MovieEntity> findByGenre(String genre);
}
