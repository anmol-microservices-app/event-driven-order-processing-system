package com.kafkaproject.orderService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kafkaproject.orderService.entity.OrderEntity;
import com.kafkaproject.orderService.entity.OutboxEvent;
import com.kafkaproject.orderService.kafkaProducerService.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	public ResponseEntity<OutboxEvent> createOrder(@RequestBody OrderEntity order) {
		OutboxEvent savedOrder = orderService.placeOrder(order);
		return ResponseEntity.ok(savedOrder);
	}
}