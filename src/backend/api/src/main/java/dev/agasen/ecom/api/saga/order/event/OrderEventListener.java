package dev.agasen.ecom.api.saga.order.event;

import dev.agasen.ecom.api.saga.order.model.PurchaseOrder;

public interface OrderEventListener {
  
  void emitOrderCreated(PurchaseOrder order);

}
