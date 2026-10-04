package com.kafkaproject.orderService.kafkaProducerService;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.kafkaproject.orderService.dto.OrderCreatedEvent;

@Service
public class OrderProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	private static final String TOPIC = "order-topic-v1";

	public OrderProducer(KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public CompletableFuture<SendResult<String, Object>> sendOrderEvent(OrderCreatedEvent event) {

		return kafkaTemplate.send(TOPIC, event.getId().toString(), event);
	}
}
