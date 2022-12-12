package com.dh.serie.event;

import com.dh.serie.config.RabbitMQConfig;
import com.dh.serie.model.Serie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class NewSerieEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public NewSerieEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void execute(Serie serie) {
        NewSerieEventProducer.Data data = new NewSerieEventProducer.Data();
        BeanUtils.copyProperties(serie, data.getSerie());
        if (data.getSerie().getSeasons() != null && serie.getSeasons() != null) {
            BeanUtils.copyProperties(serie.getSeasons(), data.getSerie().getSeasons());
        }
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.TOPIC_NEW_SERIE, data);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;
        private SerieDto serie = new SerieDto();

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
            private Integer mgCount = 0;
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
