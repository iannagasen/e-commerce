package dev.agasen.ecom.inventory.messaging;

import java.time.Instant;

import dev.agasen.ecom.api.core.inventory.event.InventoryEvent;
import dev.agasen.ecom.api.core.inventory.model.Inventory;
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

  public static InventoryEvent.Deducted toInventoryDeductedEvent(Inventory inv) {
    return InventoryEvent.Deducted.builder()
        .inventoryId(inv.id())
        .orderId(inv.orderId())
        .productId(inv.productId())
        // is this really quantity === stock
        .quantity(inv.stock())
        .createdAt(Instant.now())
        .build();
  }

  
}
