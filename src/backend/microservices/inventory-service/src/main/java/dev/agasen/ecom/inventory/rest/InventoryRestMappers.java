package dev.agasen.ecom.inventory.rest;

import java.util.List;
import java.util.function.Function;

import dev.agasen.ecom.api.core.inventory.model.Inventory;
import dev.agasen.ecom.api.core.inventory.model.InventoryUpdate;
import dev.agasen.ecom.inventory.persistence.InventoryEntity;

public record InventoryRestMappers() {
  
  public static Function<InventoryEntity, Inventory> inventoryMapper(List<InventoryUpdate> history) {
    return e -> Inventory.builder()
        .id(e.getInventoryId())
        .productId(e.getProductId())
        .stock(e.getStock())
        .history(history)
        .build();
  }

}
