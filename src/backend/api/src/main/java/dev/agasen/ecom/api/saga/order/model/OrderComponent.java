package dev.agasen.ecom.api.saga.order.model;

import java.time.Instant;

import dev.agasen.ecom.api.core.inventory.event.InventoryStatus;
import dev.agasen.ecom.api.core.payment.event.PaymentStatus;
import dev.agasen.ecom.api.core.shipping.event.ShippingStatus;
import dev.agasen.ecom.api.saga.order.model.OrderComponent.OrderInventory;
import dev.agasen.ecom.api.saga.order.model.OrderComponent.OrderPayment;
import dev.agasen.ecom.api.saga.order.model.OrderComponent.OrderShipment;
import lombok.Builder;


public sealed interface OrderComponent permits OrderInventory, OrderPayment, OrderShipment {
  
  Long orderId();
  boolean success();
  String message();

  @Builder
  record OrderInventory(Long orderId,
                        Long inventoryId,
                        InventoryStatus status,
                        boolean success,
                        String message) implements OrderComponent { }


  @Builder
  record OrderPayment(Long orderId,
                      Long paymentId,
                      boolean success,
                      PaymentStatus status,
                      String message) implements OrderComponent {}
                      

  @Builder
  record OrderShipment(Long orderId,
                       Long shippingId,
                       boolean success,
                       ShippingStatus status,
                       Instant deliveryDate,
                       String message) implements OrderComponent { }



}
