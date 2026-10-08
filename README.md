# Event-Driven Order Processing System

A production-oriented event-driven order processing system built using **Java, Spring Boot, Apache Kafka, Spring Data JPA, and Oracle/H2-compatible persistence**.

The project demonstrates how a order processing application can use asynchronous event-driven communication to decouple order creation from downstream notification processing.

---

## 👨‍💻 Author

**Anmol Kumar Singh**  
Software Developer

---

## 📌 Project Overview

The system follows an event-driven microservices architecture where the creation of an order produces an event that is consumed asynchronously by a notification/email service.

Instead of tightly coupling the order creation flow with email notification processing, the producer publishes a `OrderCreatedEvent` to Kafka. The consumer independently processes the event and triggers the notification workflow.

### High-Level Flow

```text
Order
   |
   | Create Order
   v
+------------------------+
| Kafka Producer Service |
+------------------------+
   |
   | OrderCreatedEvent
   v
+------------------------+
|     Apache Kafka       |
|  Order-created-topic   |
+------------------------+
   |
   v
+-----------------------------+
| Email Notification Service  |
|        Kafka Consumer        |
+-----------------------------+
   |
   +--------------------+
   |                    |
   v                    v
Processed Event       Email Service
    Database               |
                           v
                    Notification
