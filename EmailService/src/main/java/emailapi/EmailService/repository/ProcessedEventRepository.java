package emailapi.EmailService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import emailapi.EmailService.entity.ProcessedEvent;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, String> {
	public boolean existsByEventId(String eventId);
}