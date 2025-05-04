package com.example.productmicroservice.service;

import by.antohakon.core.ProductCreatedEvent;
import com.example.productmicroservice.Dto.CreateProductDto;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ProductServiceImpl implements ProductService {

    private KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductDto createProductDto)  throws ExecutionException, InterruptedException {

        //TODO Save product to database

        LOGGER.info("Product created: {} {} {}", createProductDto.getTittle(), createProductDto.getPrice(), createProductDto.getQuantity());

        String productId = UUID.randomUUID().toString();

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId, createProductDto.getTittle(),
                createProductDto.getPrice(), createProductDto.getQuantity());


        ProducerRecord<String, ProductCreatedEvent> record = new ProducerRecord<>(
                "product-created-events-topic",
                productId,
                productCreatedEvent
        );

        record.headers().add("messageId", UUID.randomUUID().toString().getBytes());
        //record.headers().add("messageId", "querry".getBytes());
        // synchronous
        SendResult<String, ProductCreatedEvent> result = kafkaTemplate
              .send(record).get();

        LOGGER.info("Topic: {}", result.getRecordMetadata().topic());
        LOGGER.info("Partition: {}", result.getRecordMetadata().partition());
        LOGGER.info("Offset: {}", result.getRecordMetadata().offset());

        LOGGER.info("RETURN Product created: {}", productId);


//    ДЛЯ АСИНХРОННОГ РЕЖИМА
// async

//  CompletableFuture<SendResult<String, ProductCreatedEvent>> future = kafkaTemplate
//       .send("product-created-events-topic", productId, productCreatedEvent>

//       CompletableFuture<SendResult<String, ProductCreatedEvent>> future = kafkaTemplate
//               .send("product-created-events-topic", productId, productCreatedEvent);
//
//       future.whenComplete((result, exception) -> {
//           if (exception != null) {
//               LOGGER.error("FAiled to send message {}", exception.getMessage());
//           } else {
//               LOGGER.info("Product created event sent successfully: {}", result.getRecordMetadata());
//           }
//       });
//
//      // future.join(); // для синхронного режима

       LOGGER.info("RETURN Product created: {}", productId);

        return productId;
    }
}
