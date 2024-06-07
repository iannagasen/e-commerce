package dev.agasen.ecom.inventory.persistence;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import dev.agasen.ecom.api.core.inventory.model.InventoryUpdateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="inventory_updates")
public class InventoryUpdateEntity {
 
  private @Id String id;
  private @Indexed Long productId;
  private Long orderId;
  private InventoryUpdateType type;
  private int quantity;
  private LocalDateTime dateTime;

  public InventoryUpdateEntity(Long productId, Long orderId, InventoryUpdateType type, int quantity) {
    this.id = null;
    this.productId = productId;
    this.orderId = orderId;
    this.type = type;
    this.quantity = quantity;
    this.dateTime = LocalDateTime.now();
  }

}