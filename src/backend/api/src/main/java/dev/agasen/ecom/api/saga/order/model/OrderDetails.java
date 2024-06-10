package dev.agasen.ecom.api.saga.order.model;

import dev.agasen.ecom.api.saga.order.model.OrderComponent.OrderInventory;
import dev.agasen.ecom.api.saga.order.model.OrderComponent.OrderPayment;
import dev.agasen.ecom.api.saga.order.model.OrderComponent.OrderShipment;
import lombok.Builder;

@Builder
public record OrderDetails(PurchaseOrder order, 
                           OrderInventory inventory, 
                           OrderShipment shipping, 
                           OrderPayment payment) {
  
}
