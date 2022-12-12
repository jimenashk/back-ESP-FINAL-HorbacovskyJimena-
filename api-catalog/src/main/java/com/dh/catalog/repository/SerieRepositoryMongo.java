package com.dh.catalog.repository;

import com.dh.catalog.model.SerieEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SerieRepositoryMongo extends MongoRepository<SerieEntity, Long> {

    List<SerieEntity> findByGenre(String genre);
}
