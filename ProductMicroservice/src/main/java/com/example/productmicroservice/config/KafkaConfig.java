package com.example.productmicroservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic createTopic(){
        return TopicBuilder
                .name("product-created-events-topic")
                .partitions(3)
                .replicas(3) // поменяем когда запустим 3 сервера кафки в кластере
                .configs(Map.of("min.insync.replicas", "2")) // 2 сервера должны быть в синхроне , это тоже когда будет 3 сервера кафки
                .build();
    }
}
