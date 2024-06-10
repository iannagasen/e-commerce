package dev.agasen.ecom.api.saga.order.model;

import java.time.Instant;

import dev.agasen.ecom.api.saga.order.event.OrderStatus;
import lombok.Builder;

@Builder
public record PurchaseOrder(
  Long orderId,
  Long productId,
  Long customerId,
  int quantity,
  int unitPrice,
  int amount,
  OrderStatus orderStatus,
  Instant deliveryDate
) {
  
}
