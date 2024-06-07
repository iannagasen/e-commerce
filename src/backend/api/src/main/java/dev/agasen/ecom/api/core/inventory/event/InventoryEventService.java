package dev.agasen.ecom.api.core.inventory.event;

import dev.agasen.ecom.api.core.inventory.model.Inventory;
import dev.agasen.ecom.api.core.inventory.model.InventoryDeductionRequest;
import reactor.core.publisher.Mono;

public interface InventoryEventService {
  
  Mono<Inventory> deduct(InventoryDeductionRequest request);

  Mono<Inventory> restore(Long orderId);
}
