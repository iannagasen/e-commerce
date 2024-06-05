package dev.agasen.ecom.payment.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import dev.agasen.ecom.api.core.payment.event.PaymentStatus;
import reactor.core.publisher.Mono;

public interface CustomerPaymentRepository extends ReactiveMongoRepository<CustomerPaymentEntity, String> {
  
  Mono<Boolean> existsByOrderId(Long orderId);

  Mono<CustomerPaymentEntity> findByOrderId(Long orderId);

  default Mono<CustomerPaymentEntity> setPaymentStatus(CustomerPaymentEntity entity, PaymentStatus status) {
    entity.setStatus(status);
    return save(entity);
  }

}
