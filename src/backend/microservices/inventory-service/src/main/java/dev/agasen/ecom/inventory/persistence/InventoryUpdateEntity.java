package dev.agasen.ecom.inventory.persistence;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import dev.agasen.ecom.api.core.inventory.model.InventoryUpdateType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="inventory_updates")
public class InventoryUpdateEntity {
 
  private @Id String id;
  private Long productId;
  private InventoryUpdateType type;
  private LocalDateTime dateTime;

}