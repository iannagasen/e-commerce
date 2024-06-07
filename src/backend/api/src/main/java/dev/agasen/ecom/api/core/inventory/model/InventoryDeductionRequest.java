package dev.agasen.ecom.api.core.inventory.model;

import lombok.Builder;

@Builder
public record InventoryDeductionRequest(
  Long productId,
  Long orderId,
  Long customerId,
  int quantity
) {
  
}
