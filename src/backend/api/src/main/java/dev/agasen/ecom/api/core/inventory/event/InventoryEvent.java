package dev.agasen.ecom.api.core.inventory.event;

import java.time.Instant;

import dev.agasen.ecom.api.event.DomainEvent;
import dev.agasen.ecom.api.event.OrderSaga;
import lombok.Builder;

public interface InventoryEvent extends DomainEvent, OrderSaga {
  
  @Builder
  record Deducted(Long orderId,
                  Long inventoryId,
                  Long productId,
                  int quantity,
                  Instant createdAt) implements InventoryEvent {};
  @Builder
  record Restored(Long orderId,
                  Long inventoryId,
                  Long productId,
                  int quantity,
                  Instant createdAt) implements InventoryEvent {};

    @Builder
  record Declined(Long orderId,
                  Long productId,
                  int quantity,
                  String message,
                  Instant createdAt) implements InventoryEvent {};

    
}
