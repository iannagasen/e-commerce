package dev.agasen.ecom.payment;

import java.time.Instant;
import java.util.Random;

import dev.agasen.ecom.api.core.order.event.OrderEvent;

public record KafkaTestDataUtils() {

  public static OrderEvent.Created createOrderCreatedEvent(Long customerId, Long productId, int unitPrice, int quantity) {
    return OrderEvent.Created.builder()
        .orderId(new Random().nextLong())
        .createdAt(Instant.now())
        .totalAmount(unitPrice * quantity)
        .unitPrice(unitPrice)
        .quantity(quantity)
        .customerId(customerId)
        .productId(productId)
        .build();
  }

  public static OrderEvent.Cancelled createOrderCancelledEvent(Long orderId) {
    return OrderEvent.Cancelled.builder()
        .orderId(orderId)
        .createdAt(Instant.now())
        .build();
  }

}
