package dev.agasen.ecom.inventory.rest;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import dev.agasen.ecom.api.core.inventory.model.Inventory;
import dev.agasen.ecom.api.core.inventory.model.InventoryDeductionRequest;
import dev.agasen.ecom.api.core.inventory.model.InventoryUpdate;
import dev.agasen.ecom.inventory.persistence.InventoryEntity;
import dev.agasen.ecom.inventory.persistence.InventoryUpdateEntity;

public record InventoryRestMappers() {

  public static Inventory map(InventoryEntity entity, List<InventoryUpdate> history) {
    return Inventory.builder()
        .id(entity.getInventoryId())
        .productId(entity.getProductId())
        .stock(entity.getStock())
        .history(history)
        .build();
  } 

  public static Inventory map(InventoryEntity entity) {
    return map(entity, Collections.emptyList());
  }

}
