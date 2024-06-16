package dev.agasen.ecom.order.messaging;

import org.springframework.stereotype.Service;

import dev.agasen.ecom.api.saga.order.event.OrderFulfillmentAbstractService;
import dev.agasen.ecom.api.saga.order.event.OrderStatus;
import dev.agasen.ecom.api.saga.order.model.PurchaseOrder;
import dev.agasen.ecom.order.persistence.OrderComponentRepository;
import dev.agasen.ecom.order.persistence.PurchaseOrderEntity;
import dev.agasen.ecom.order.persistence.PurchaseOrderRepository;
import dev.agasen.ecom.order.util.OrderMapper;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderFullfilmentService implements OrderFulfillmentAbstractService {

  private final OrderComponentRepository repo;
  private final PurchaseOrderRepository purchaseOrderRepository;

  @Override
  public Mono<PurchaseOrder> complete(Long orderId) {
    return repo.findAllByOrderId(orderId).collectList()
        .filter(components -> components.stream().allMatch(component -> component.isSuccessful()))
        .flatMap(components -> purchaseOrderRepository.findByOrderId(orderId))
        .doOnNext(purchaseOrder -> purchaseOrder.setStatus(OrderStatus.COMPLETED))
        .flatMap(purchaseOrderRepository::save)
        .map(OrderMapper::toPurchaseOrder);
  }

  @Override
  public Mono<PurchaseOrder> cancel(Long orderId) {
    return purchaseOrderRepository.findByOrderId(orderId)
        .filter(order -> order.getStatus() == OrderStatus.PENDING)
        .doOnNext(order -> order.setStatus(OrderStatus.CANCELLED))
        .flatMap(purchaseOrderRepository::save)
        .map(OrderMapper::toPurchaseOrder);
  }
  
}
