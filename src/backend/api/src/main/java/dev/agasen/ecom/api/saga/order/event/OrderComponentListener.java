package dev.agasen.ecom.api.saga.order.event;

import dev.agasen.ecom.api.event.saga.SagaComponentListener;
import dev.agasen.ecom.api.saga.order.model.OrderComponent;
import reactor.core.publisher.Mono;

public interface OrderComponentListener<T> extends SagaComponentListener<T> {
  
  interface Payment extends OrderComponentListener<OrderComponent.OrderPayment> {}

  interface Inventory extends OrderComponentListener<OrderComponent.OrderInventory> {}

  interface Shipping extends OrderComponentListener<OrderComponent.OrderShipment> {}

}
