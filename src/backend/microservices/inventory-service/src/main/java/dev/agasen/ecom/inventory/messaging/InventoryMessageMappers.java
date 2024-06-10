package dev.agasen.ecom.inventory.messaging;

import dev.agasen.ecom.api.core.inventory.model.InventoryDeductionRequest;
import dev.agasen.ecom.api.saga.order.event.OrderEvent;

public record InventoryMessageMappers() {

  public static InventoryDeductionRequest toInventoryDeductionRequest(OrderEvent.Created orderCreatedEvent) {
    return InventoryDeductionRequest.builder()
        .quantity(orderCreatedEvent.quantity())
        .productId(orderCreatedEvent.orderId())
        .orderId(orderCreatedEvent.orderId())
        .customerId(orderCreatedEvent.customerId())
        .build();
  }
  
}
