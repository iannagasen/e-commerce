package dev.agasen.ecom.api.core.inventory.model;

import java.time.LocalDateTime;

public record InventoryUpdate(
  Long productId,
  InventoryUpdateType type,
  LocalDateTime dateTime
) { }