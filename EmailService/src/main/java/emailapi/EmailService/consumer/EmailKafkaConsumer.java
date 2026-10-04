package emailapi.EmailService.consumer;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import emailapi.EmailService.dto.OrderCreatedEvent;
import emailapi.EmailService.entity.ProcessedEvent;
import emailapi.EmailService.repository.ProcessedEventRepository;
import emailapi.EmailService.service.EmailSrevice;

@Service
public class EmailKafkaConsumer {

	private final EmailSrevice emailService;
	private final ProcessedEventRepository processedEventRepository;

	public EmailKafkaConsumer(EmailSrevice emailService, ProcessedEventRepository processedEventRepository) {

		this.emailService = emailService;
		this.processedEventRepository = processedEventRepository;
	}

	@KafkaListener(topics = "order-topic-v1", groupId = "email-group-v1")
	public void consumeOrder(OrderCreatedEvent event) {
		String eventId = String.valueOf(event.getId());
		System.out.println("================================");
		System.out.println("Email Service received Kafka event");
		System.out.println("Event ID : " + eventId);
		System.out.println("Customer Email : " + event.getCustomerEmail());
		System.out.println("================================");
		if (processedEventRepository.existsByEventId(eventId)) {
			System.out.println("Duplicate event detected. Skipping email. Event ID : " + eventId);
			return;
		}
		boolean sent = emailService.sendEmail(event.getCustomerEmail(),
				event.getProductName() + " with quantity :" + event.getQuantity(), "order created successfully...!");
		if (!sent) {
			System.out.println("Email sending failed");
			throw new RuntimeException("Email sending failed for event ID : " + eventId);
		}
		ProcessedEvent processedEvent = new ProcessedEvent();
		processedEvent.setEventId(eventId);
		processedEvent.setProcessedAt(LocalDateTime.now());
		processedEventRepository.save(processedEvent);
		System.out.println("Email sent successfully");
		System.out.println("Event marked as processed : " + eventId);
	}
}