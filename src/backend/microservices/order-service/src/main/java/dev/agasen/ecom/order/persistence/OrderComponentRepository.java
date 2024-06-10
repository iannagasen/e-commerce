package dev.agasen.ecom.order.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface OrderComponentRepository extends ReactiveMongoRepository<OrderComponentEntity, String> {

  Mono<OrderComponentEntity> findByOrderId(Long orderId);

  default Mono<OrderComponentEntity.Payment> findOrderPaymentByOrderId(Long orderId) {
    return this.findByOrderId(orderId).cast(OrderComponentEntity.Payment.class);
  }

  default Mono<OrderComponentEntity.Inventory> findOrderInventoryByOrderId(Long orderId) {
    return this.findByOrderId(orderId).cast(OrderComponentEntity.Inventory.class);
  }

}
