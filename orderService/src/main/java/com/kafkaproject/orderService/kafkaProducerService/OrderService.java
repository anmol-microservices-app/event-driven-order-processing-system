package com.kafkaproject.orderService.kafkaProducerService;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkaproject.orderService.dto.OrderCreatedEvent;
import com.kafkaproject.orderService.entity.OrderEntity;
import com.kafkaproject.orderService.entity.OutboxEvent;
import com.kafkaproject.orderService.repository.OrderRepository;
import com.kafkaproject.orderService.repository.OutboxEventRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final OutboxEventRepository repository;
	private final ObjectMapper objectMapper;

	public OrderService(OrderRepository orderRepository, OutboxEventRepository repository, ObjectMapper objectMapper) {

		this.orderRepository = orderRepository;
		this.repository = repository;
		this.objectMapper = objectMapper;
	}

	@Transactional
	public OutboxEvent placeOrder(OrderEntity entity) {

		OrderEntity savedOrder = orderRepository.save(entity);

		OrderCreatedEvent event = new OrderCreatedEvent(savedOrder.getId(), savedOrder.getProductName(),
				savedOrder.getQuantity(), savedOrder.getPrice(), savedOrder.getCustomerEmail());

		String payload;

		try {
			payload = objectMapper.writeValueAsString(event);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to create Outbox Event payload", e);
		}

		OutboxEvent eventEntity = new OutboxEvent();

		eventEntity.setAggregateType("ORDER");
		eventEntity.setAggregateId(savedOrder.getId().toString());
		eventEntity.setEventType("OrderCreated");
		eventEntity.setPayload(payload);
		eventEntity.setStatus("NEW");
		eventEntity.setCreatedAt(LocalDateTime.now());
		eventEntity.setRetryCount(0);

		OutboxEvent savedEvent = repository.save(eventEntity);

		return savedEvent;
	}
}
