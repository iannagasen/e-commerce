package dev.agasen.ecom.api.core.inventory.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import dev.agasen.ecom.api.core.inventory.model.Inventory;
import dev.agasen.ecom.api.core.inventory.model.InventoryUpdate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InventoryRestService {

  @GetMapping("/inventory/{productId}")
  Mono<Inventory> getInventory(@PathVariable Long productId);

  @GetMapping("/inventory")
  Flux<Inventory> getInventories();

  @GetMapping("/inventory/{productId}/history")
  Flux<InventoryUpdate> getInventoryHistory(@PathVariable Long productId);
  
}