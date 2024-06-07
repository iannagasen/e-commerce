package dev.agasen.ecom.api.core.inventory.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

@Builder
public record Inventory(
  Long id,
  Long productId,
  int stock,
  @JsonInclude(JsonInclude.Include.NON_NULL) List<InventoryUpdate> history
) { }
