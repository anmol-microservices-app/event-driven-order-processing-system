package com.kafkaproject.orderService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kafkaproject.orderService.entity.OutboxEvent;

@Repository
public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

	List<OutboxEvent> findTop100ByStatusOrderByCreatedAtAsc(String status);
}