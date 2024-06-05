package dev.agasen.ecom.api.core.payment.event;

import java.time.Instant;

import dev.agasen.ecom.api.event.DomainEvent;
import dev.agasen.ecom.api.event.OrderSaga;
import lombok.Builder;

public interface PaymentEvent extends DomainEvent, OrderSaga {
  
  @Builder
  record Deducted(Long orderId,
                  Instant createdAt,
                  String paymentId,
                  Long customerId,
                  Long amount) implements PaymentEvent {};

  @Builder
  record Refunded(Long orderId,
                  String paymentId,
                  Long customerId,
                  Long amount,
                  Instant createdAt) implements PaymentEvent {};

  @Builder
  record Declined(Long orderId,
                  Long customerId,
                  String message,
                  Long amount,
                  Instant createdAt) implements PaymentEvent {};
}
