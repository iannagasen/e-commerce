package dev.agasen.ecom.api.core.inventory.model;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record InventoryUpdate(
  Long productId,
  InventoryUpdateType type,
  int quantity,
  LocalDateTime dateTime
) { }