package by.antohakon.emailnotificationsmicroservice.repository;

import by.antohakon.emailnotificationsmicroservice.entity.ProcessedEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEventEntity, Long> {

     ProcessedEventEntity findByMessageId(String messageId);

}
