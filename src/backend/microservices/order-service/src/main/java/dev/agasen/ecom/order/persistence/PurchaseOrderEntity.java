package dev.agasen.ecom.order.persistence;

import java.time.Instant;

// import dev.agasen.ecom.api.saga.order.event.OrderStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import dev.agasen.ecom.api.saga.order.event.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection="purchase_orders")
public class PurchaseOrderEntity {

  private @Id String id;
  private @Indexed(unique=true) Long orderId;
  private Long productId;
  private Long customerId;
  private int quantity;
  private int unitPrice;
  private int amount;
  private OrderStatus status;
  private Instant deliveryDate;
  
}
