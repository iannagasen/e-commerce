package dev.agasen.ecom.api.saga.order.event;

import dev.agasen.ecom.api.saga.order.model.OrderCreateRequest;
import dev.agasen.ecom.api.saga.order.model.OrderDetails;
import dev.agasen.ecom.api.saga.order.model.PurchaseOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderEventAbstractService {
  
  Mono<PurchaseOrder> placeOrder(OrderCreateRequest req);

  Flux<PurchaseOrder> getAllOrders();

  Mono<OrderDetails> getOrderDetails(Long orderId);

}