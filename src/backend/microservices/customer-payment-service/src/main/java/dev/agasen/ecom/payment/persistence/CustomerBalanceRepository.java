package dev.agasen.ecom.payment.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface CustomerBalanceRepository extends ReactiveMongoRepository<CustomerBalanceEntity, String> {

  Mono<CustomerBalanceEntity> findByCustomerId(Long id);

  default Mono<CustomerBalanceEntity> setBalance(CustomerBalanceEntity customerBalance, Long balance) {
    customerBalance.setBalance(balance);
    return save(customerBalance);
  }
}
