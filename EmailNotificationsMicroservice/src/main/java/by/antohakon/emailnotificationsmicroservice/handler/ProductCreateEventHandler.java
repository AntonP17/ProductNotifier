package by.antohakon.emailnotificationsmicroservice.handler;

import by.antohakon.core.ProductCreatedEvent;
import by.antohakon.emailnotificationsmicroservice.entity.ProcessedEventEntity;
import by.antohakon.emailnotificationsmicroservice.repository.ProcessedEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@KafkaListener(topics = "product-created-events-topic", groupId = "product-created-events")
public class ProductCreateEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ProcessedEventRepository processedEventRepository;

    public ProductCreateEventHandler(ProcessedEventRepository processedEventRepository) {
        this.processedEventRepository = processedEventRepository;
    }

    @Transactional
    @KafkaHandler
    public void handle(@Payload ProductCreatedEvent productCreatedEvent,
                       @Header("messageId") String messageId,
                       @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {

        LOGGER.info("Product created event: {}" , productCreatedEvent.getTittle());

       ProcessedEventEntity processedEventEntity = processedEventRepository.findByMessageId(messageId);

       if (processedEventEntity != null) {
           LOGGER.info("DUPLICATE Product created event: {}", messageId);
           return;
       }

        try {
            processedEventRepository.save(new ProcessedEventEntity(messageId, productCreatedEvent.getProductId()));
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(e.getMessage());
        }

    }
}
