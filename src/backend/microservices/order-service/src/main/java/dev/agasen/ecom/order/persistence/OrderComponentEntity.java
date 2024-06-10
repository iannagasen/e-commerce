package dev.agasen.ecom.order.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import dev.agasen.ecom.api.core.inventory.event.InventoryStatus;
import dev.agasen.ecom.api.core.payment.event.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Document(collection="order_component")
@Getter
public abstract class OrderComponentEntity {

  protected @Id String id;
  protected @Indexed(unique=true) Long componentId;
  protected @Indexed(unique=true) Long orderId;

  public abstract String getComponent();
  public abstract boolean isSuccessful();
  public abstract void setSuccessful(boolean successful);
  public abstract String getMessage();
  public abstract void setMessage(String message);


  @Getter
  @SuperBuilder
  @TypeAlias("order_inventory")
  @Document(collection="order_component")
  public static class Inventory extends OrderComponentEntity {
    private Long inventoryId;
    private @Setter InventoryStatus status;
    private @Setter boolean successful;
    private @Setter String message;
    private final String component = "order_inventory";
  }

  @Getter
  @SuperBuilder
  @TypeAlias("order_payment")
  @Document(collection="order_component")
  public static class Payment extends OrderComponentEntity {
    private Long paymentId;
    private @Setter PaymentStatus status;
    private @Setter boolean successful;
    private @Setter String message;
    private final String component = "order_payment";
  }
  
}
