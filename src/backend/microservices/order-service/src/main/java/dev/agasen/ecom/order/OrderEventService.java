package dev.agasen.ecom.order;

import org.springframework.stereotype.Service;

import dev.agasen.ecom.api.saga.order.event.OrderComponentFetcher;
import dev.agasen.ecom.api.saga.order.event.OrderEventAbstractService;
import dev.agasen.ecom.api.saga.order.event.OrderEventListener;
import dev.agasen.ecom.api.saga.order.model.OrderCreateRequest;
import dev.agasen.ecom.api.saga.order.model.OrderDetails;
import dev.agasen.ecom.api.saga.order.model.PurchaseOrder;
import dev.agasen.ecom.order.persistence.PurchaseOrderRepository;
import dev.agasen.ecom.order.util.OrderMapper;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderEventService implements OrderEventAbstractService {

  private final PurchaseOrderRepository purchaseOrderRepo;
  private final OrderEventListener eventListener;
  private final OrderComponentFetcher.Inventory inventoryFetcher;
  private final OrderComponentFetcher.Payment paymentFetcher;

  @Override
  public Mono<PurchaseOrder> placeOrder(OrderCreateRequest req) {
    var entity = OrderMapper.toPurchaseOrderEntity(req);
    return this.purchaseOrderRepo.save(entity)
        .map(OrderMapper::toPurchaseOrder)
        .doOnNext(eventListener::emitOrderCreated);
    
  }

  @Override
  public Flux<PurchaseOrder> getAllOrders() {
    // ? TODO: Maybe implement projection instead of mapping 1 by 1
    // ! What is the use of this getAllOrders()
    return this.purchaseOrderRepo.findAll()
        .map(OrderMapper::toPurchaseOrder);
  }

  @Override
  public Mono<OrderDetails> getOrderDetails(Long orderId) {
    return this.purchaseOrderRepo.findByOrderId(orderId)
        .map(OrderMapper::toPurchaseOrder)
        .flatMap(purchaseOrder -> Mono.zip(Mono.just(purchaseOrder), paymentFetcher.getComponent(orderId), inventoryFetcher.getComponent(orderId)))
        .map(tuple -> OrderMapper.toOrderDetails(tuple.getT1(), tuple.getT2(), tuple.getT3()));
  }
  
}
