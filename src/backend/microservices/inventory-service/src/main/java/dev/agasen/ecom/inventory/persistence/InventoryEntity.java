package dev.agasen.ecom.inventory.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "inventory")
public class InventoryEntity {

  public @Transient static final String SEQUENCE_NAME = "product-inventory";

  private @Id String id;
  private @Indexed(unique=true) Long inventoryId;
  private @Indexed(unique=true) Long productId;
  private int stock;

  public InventoryEntity withInventoryId(Long inventoryId) {
    this.inventoryId = inventoryId;
    return this;
  }
}
