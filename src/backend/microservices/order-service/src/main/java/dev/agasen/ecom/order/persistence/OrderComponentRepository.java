package dev.agasen.ecom.order.persistence;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderComponentRepository extends ReactiveMongoRepository<OrderComponentEntity, String> {
  
  Flux<OrderComponentEntity> findAllByOrderId(Long orderId);

  @Query("{'orderId': ?0, '_class': 'dev.agasen.ecom.order.persistence.OrderComponentEntity$Payment'}")
  Mono<OrderComponentEntity.Payment> findOrderPaymentByOrderId(Long orderId);

  @Query("{'orderId': ?0, '_class': 'dev.agasen.ecom.order.persistence.OrderComponentEntity$Inventory'}")
  Mono<OrderComponentEntity.Inventory> findOrderInventoryByOrderId(Long orderId);

  @Query("{'orderId': ?0, '_class': 'dev.agasen.ecom.order.persistence.OrderComponentEntity$Shipping'}")
  Mono<OrderComponentEntity.Shipping> findOrderShipmentByOrderId(Long orderId);


}
