package com.dh.catalog.event;

import com.dh.catalog.configuration.RabbitMQConfig;
import com.dh.catalog.model.SerieEntity;
import com.dh.catalog.repository.SerieRepositoryMongo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class NewSerieEventConsumer {

    private final SerieRepositoryMongo serieRepositoryMongo;

    public NewSerieEventConsumer(SerieRepositoryMongo serieRepositoryMongo) {
        this.serieRepositoryMongo = serieRepositoryMongo;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_SERIE)
    public void execute(NewSerieEventConsumer.Data data) {
        SerieEntity newSerie = new SerieEntity();
        BeanUtils.copyProperties(data.getSerie(), newSerie);
        if (data.getSerie().getSeasons() != null && newSerie.getSeasons() != null) {
            BeanUtils.copyProperties(data.getSerie().getSeasons(), newSerie.getSeasons());
        }
        serieRepositoryMongo.deleteById(data.getSerie().getSerieId());
        serieRepositoryMongo.save(newSerie);
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;
        private Data.SerieDto serie = new SerieDto();

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SerieDto implements Serializable {

            @Serial
            private static final long serialVersionUID = 1L;
            private Long serieId;
            private String name;
            private String genre;
            private Integer mgCount;
            private List<SeasonDto> seasons = new ArrayList<>();

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            public static class SeasonDto implements Serializable {

                @Serial
                private static final long serialVersionUID = 1L;
                private Long seasonId;
                private Integer seasonNumber;
                private List<ChapterDto> chapters = new ArrayList<>();

                @Getter
                @Setter
                @NoArgsConstructor
                @AllArgsConstructor
                public static class ChapterDto implements Serializable {

                    @Serial
                    private static final long serialVersionUID = 1L;
                    private Long chapterId;
                    private String name;
                    private String genre;
                    private String urlStream;

                }
            }
        }
    }
}
