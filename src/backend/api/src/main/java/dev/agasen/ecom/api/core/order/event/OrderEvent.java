package dev.agasen.ecom.api.core.order.event;

import java.time.Instant;

import dev.agasen.ecom.api.event.DomainEvent;
import dev.agasen.ecom.api.event.OrderSaga;
import lombok.Builder;

public interface OrderEvent extends DomainEvent, OrderSaga {

  @Builder
  record Created(Long orderId,
                 Instant createdAt,
                 Long productId,
                 Long customerId,
                 int quantity,
                 int unitPrice,
                 Long totalAmount) implements OrderEvent {}

  @Builder
  record Cancelled(Long orderId,
                   Instant createdAt,
                   String message) implements OrderEvent {};

  @Builder
  record Completted(Long orderId,
                    Instant createdAt) implements OrderEvent {};
  
}
