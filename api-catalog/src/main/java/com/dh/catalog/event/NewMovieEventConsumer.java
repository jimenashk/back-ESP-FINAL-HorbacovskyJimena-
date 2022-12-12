package com.dh.catalog.event;

import com.dh.catalog.configuration.RabbitMQConfig;
import com.dh.catalog.model.MovieEntity;
import com.dh.catalog.repository.MovieRepositoryMongo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class NewMovieEventConsumer {

    private final MovieRepositoryMongo movieRepositoryMongo;

    public NewMovieEventConsumer(MovieRepositoryMongo movieRepositoryMongo) {
        this.movieRepositoryMongo = movieRepositoryMongo;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_MOVIE)
    public void execute(NewMovieEventConsumer.Data data) {
        MovieEntity newMovie = new MovieEntity();
        BeanUtils.copyProperties(data.getMovie(), newMovie);
        movieRepositoryMongo.deleteById(data.getMovie().getMovieId());
        movieRepositoryMongo.save(newMovie);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data implements Serializable {
        private MovieDto movie=new MovieDto();

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MovieDto{
            private Long movieId;
            private String name;
            private String genre;
            private String urlStream;
            private Integer mgCount;
        }

    }
}
