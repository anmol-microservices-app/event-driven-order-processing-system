package com.kafkaproject.orderService.outbox;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkaproject.orderService.dto.OrderCreatedEvent;
import com.kafkaproject.orderService.entity.OutboxEvent;
import com.kafkaproject.orderService.kafkaProducerService.OrderProducer;
import com.kafkaproject.orderService.repository.OutboxEventRepository;

@Component
public class OutboxPublisher {

	// to start kafka need to set below data--------------------------------------------
	// set KAFKA_HEAP_OPTS=-Xmx1G -Xms1G
	// bin\windows\kafka-server-start.bat config\server.properties

	private final OutboxEventRepository repository;
	private final OrderProducer orderProducer;
	private final ObjectMapper objectMapper;

	public OutboxPublisher(OutboxEventRepository repository, OrderProducer orderProducer, ObjectMapper objectMapper) {

		this.repository = repository;
		this.orderProducer = orderProducer;
		this.objectMapper = objectMapper;
	}

	@Scheduled(fixedDelay = 5000)
	public void publishEvents() {

		List<OutboxEvent> events = repository.findTop100ByStatusOrderByCreatedAtAsc("NEW");

		for (OutboxEvent outboxEvent : events) {

			try {

				// Convert JSON payload back to Java object
				OrderCreatedEvent event = objectMapper.readValue(outboxEvent.getPayload(), OrderCreatedEvent.class);

				// Send event to Kafka
				orderProducer.sendOrderEvent(event).whenComplete((result, exception) -> {
					if (exception == null) {
						outboxEvent.setStatus("PUBLISHED");
						outboxEvent.setPublishedAt(LocalDateTime.now());
						repository.save(outboxEvent);
						System.out.println("Outbox event published successfully. ID = " + outboxEvent.getId());
					} else {
						// Kafka send failed
						int retryCount = outboxEvent.getRetryCount() == null ? 0 : outboxEvent.getRetryCount();
						outboxEvent.setRetryCount(retryCount + 1);
						repository.save(outboxEvent);
						System.out.println("Failed to publish outbox event. ID = " + outboxEvent.getId() + ", error = "
								+ exception.getMessage());
					}
				});

			} catch (Exception e) {

				int retryCount = outboxEvent.getRetryCount() == null ? 0 : outboxEvent.getRetryCount();

				outboxEvent.setRetryCount(retryCount + 1);

				repository.save(outboxEvent);

				System.out.println(
						"Failed to process outbox event. ID = " + outboxEvent.getId() + ", error = " + e.getMessage());
			}
		}
	}
}
