package dev.agasen.ecom.api.saga.order.event;

import dev.agasen.ecom.api.event.saga.SagaComponentFetcher;
import dev.agasen.ecom.api.saga.order.model.OrderComponent;

public interface OrderComponentFetcher<T> extends SagaComponentFetcher<T, Long>{

  interface Payment extends OrderComponentFetcher<OrderComponent.OrderPayment> {}

  interface Inventory extends OrderComponentFetcher<OrderComponent.OrderInventory> {}

  interface Shipping extends OrderComponentFetcher<OrderComponent.OrderShipment> {}

}
