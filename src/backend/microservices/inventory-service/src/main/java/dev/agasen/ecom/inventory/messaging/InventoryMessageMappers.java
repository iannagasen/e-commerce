package dev.agasen.ecom.inventory.messaging;

import java.util.function.Function;

import dev.agasen.ecom.api.core.inventory.model.InventoryDeductionRequest;
import dev.agasen.ecom.api.core.order.event.OrderEvent;

public record InventoryMessageMappers() {

  public static Function<OrderEvent.Created, InventoryDeductionRequest> toInventoryDeductionRequest() {
    return orderCreatedEvent -> InventoryDeductionRequest.builder()
        .quantity(orderCreatedEvent.quantity())
        .productId(orderCreatedEvent.orderId())
        .orderId(orderCreatedEvent.orderId())
        .customerId(orderCreatedEvent.customerId())
        .build();
  }
  
}
